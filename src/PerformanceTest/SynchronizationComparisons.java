package PerformanceTest;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import static net.mindview.util.Print.*;
/*
由于 BaseLine 和 AtomicTest 并没有对accumulate() 方法同步，而作者写的代码没有考虑到多线程修改 index 的问题，
可能导致index 越界 ，程序崩溃！ 在这个示例代码中一次 timedTest() 测试会启动N个 Modifier 对象（在N个线程中）同时对同一个 index 进行修改，可能存在某个线程对 index++ 完成后任务被中断（判断 index 是否越界和置0的代码还没有被执行） ，另外一个线程又调用了 index++，这样index 就有可能超出SIZE的大小！
在我的机子上（jre6 + Intel i5 两核处理器），运行几次都会出现越界崩溃！

由于这是性能测试，所以不能加锁，但可以通过赋值到临时变量i，并提前进行越界判断调整i和index （见下），使得程序能够避免崩溃，正常进行性能测试。

  public void accumulate() {

int i = index++;

if(i >= SIZE){

index = 0;

i = 0;

}

value += preLoaded[i];

  }



  public void accumulate() {

    int i = index.getAndIncrement();

    if(i >= SIZE){

            i = 0;

            index.set(0);

    }

    value.getAndAdd(preLoaded[i]);

  }
 */
abstract class Accumulator {
    public static long cycles = 50000L;
    // Number of Modifiers and Readers during each test:
    private static final int N = 4;
    public static ExecutorService exec =
            Executors.newFixedThreadPool(N*2);
    private static CyclicBarrier barrier =
            new CyclicBarrier(N*2 + 1);
    protected volatile int index = 0;
    protected volatile long value = 0;
    protected long duration = 0;
    protected String id = "error";
    protected final static int SIZE = 100000;
    protected static int[] preLoaded = new int[SIZE];
    static {
        // Load the array of random numbers:
        Random rand = new Random(47);
        for(int i = 0; i < SIZE; i++)
            preLoaded[i] = rand.nextInt();
    }
    public abstract void accumulate();
    public abstract long read();
    private class Modifier implements Runnable {
        public void run() {
            for(long i = 0; i < cycles; i++)
                accumulate();
            try {
                barrier.await();
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    private class Reader implements Runnable {
        private volatile long value;
        public void run() {
            for(long i = 0; i < cycles; i++)
                value = read();
            try {
                barrier.await();
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void timedTest() {
        long start = System.nanoTime();
        for(int i = 0; i < N; i++) {
            exec.execute(new Modifier());
            exec.execute(new Reader());
        }
        try {
            barrier.await();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
        duration = System.nanoTime() - start;
        printf("%-13s: %13dn", id, duration);
    }
    public static void
    report(Accumulator acc1, Accumulator acc2) {
        printf("%-22s: %.2fn", acc1.id + "/" + acc2.id,
                (double)acc1.duration/(double)acc2.duration);
    }
}
class BaseLine extends Accumulator {
    { id = "BaseLine"; }
    public void accumulate() {
        int i = index++;
        if(i >= SIZE){
            index = 0;
            i = 0;
        }
        value += preLoaded[i];

    }
    public long read() { return value; }
}
class SynchronizedTest extends Accumulator {
    { id = "synchronized"; }
    public synchronized void accumulate() {
        value += preLoaded[index++];
        if(index >= SIZE) index = 0;
    }
    public synchronized long read() {
        return value;
    }
}
class LockTest extends Accumulator {
    { id = "Lock"; }
    private Lock lock = new ReentrantLock();
    public void accumulate() {
        lock.lock();
        try {
            value += preLoaded[index++];
            if(index >= SIZE) index = 0;
        } finally {
            lock.unlock();
        }
    }
    public long read() {
        lock.lock();
        try {
            return value;
        } finally {
            lock.unlock();
        }
    }
}
class AtomicTest extends Accumulator {
    { id = "Atomic"; }
    private AtomicInteger index = new AtomicInteger(0);
    private AtomicLong value = new AtomicLong(0);
    public void accumulate() {
        int i = index.getAndIncrement();
        if(i >= SIZE){
            i = 0;
            index.set(0);
        }

        value.getAndAdd(preLoaded[i]);

    }
    public long read() { return value.get(); }
}
public class SynchronizationComparisons {
    static BaseLine baseLine = new BaseLine();
    static SynchronizedTest synch = new SynchronizedTest();
    static LockTest lock = new LockTest();
    static AtomicTest atomic = new AtomicTest();
    static void test() {
        print("============================");
        printf("%-12s : %13dn", "Cycles", Accumulator.cycles);
        baseLine.timedTest();
        synch.timedTest();
        lock.timedTest();
        atomic.timedTest();
        Accumulator.report(synch, baseLine);
        Accumulator.report(lock, baseLine);
        Accumulator.report(atomic, baseLine);
        Accumulator.report(synch, lock);
        Accumulator.report(synch, atomic);
        Accumulator.report(lock, atomic);
    }
    public static void main(String[] args) {
        int iterations = 5; // Default
        if(args.length > 0) // Optionally change iterations
            iterations = new Integer(args[0]);
        // The first time fills the thread pool:
        print("Warmup");
        baseLine.timedTest();
        // Now the initial test doesn't include the cost
        // of starting the threads for the first time.
        // Produce multiple data points:
        for(int i = 0; i < iterations; i++) {
            test();
            Accumulator.cycles *= 2;
        }
        Accumulator.exec.shutdown();
    }
}
