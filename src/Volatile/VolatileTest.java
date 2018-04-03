package Volatile;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/*
对volatile变量在并发环境下自增运算非线程安全测试
 */
public class VolatileTest {
//    public static volatile int race = 0;
public static AtomicInteger race = new AtomicInteger(0);
    public static void increment() {
         race.incrementAndGet();
    }

    private static final int THREADS_COUNT = 20;

    public static void main(String... args) throws InterruptedException {
        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++)
                        increment();

                }
            });
            threads[i].start();
        }
        for (Thread iThread : threads) {
            try {
                // 等待所有线程执行完毕
                iThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        System.out.println(race);
    }
}
