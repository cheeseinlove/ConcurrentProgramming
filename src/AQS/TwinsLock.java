package AQS;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/*
twins lock is designed for limiting at most  two threads accessing based by AQS
 */
public class TwinsLock implements Lock {
    private final Sync sync = new Sync(2);

    private static final class Sync extends AbstractQueuedLongSynchronizer {
        Sync(int count) {
            setState(count);
        }

        @Override
        protected long tryAcquireShared(long arg) {
            for (; ; ) {
                long current = getState();
                long newCount = current - arg;
                System.out.println(getState());
                if(newCount>0){
                    System.out.println("get lock"+getState());
                    compareAndSetState(current, newCount);
                }
                return newCount;
//                if (newCount < 0 || compareAndSetState(current, newCount)){
//                    System.out.println(getState());
//                    return newCount;
//            }
            }
        }

        @Override
        protected boolean tryReleaseShared(long arg) {
            for (; ; ) {
                long current = getState();
                long newCount = current + arg;
                if (compareAndSetState(current, newCount)) {
//                    System.out.println(getState());
                    return true;
                }
            }}
    }

    @Override
    public void lock() {
        sync.tryAcquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquireShared(1)>0;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        sync.tryReleaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return sync.new ConditionObject();
    }
}
