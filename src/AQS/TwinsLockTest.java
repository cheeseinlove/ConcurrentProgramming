package AQS;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class TwinsLockTest {
    public void test(){
        final Lock lock=new TwinsLock();
        class Worker extends Thread{
            @Override
            public void run() {

                    lock.lock();
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println(Thread.currentThread().getName());
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {

                        lock.unlock();
                    }


            }
        }
        for(int i=0;i<10;i++){
            Worker worker=new Worker();
//            worker.setDaemon(true);
            worker.start();
        }
//        for(int i=0;i<10;i++)
//        {
//            try {
//                TimeUnit.SECONDS.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println();
//
//        }
    }
    public static void main(String...args){
        TwinsLockTest s=new TwinsLockTest();
        s.test();

    }
}
