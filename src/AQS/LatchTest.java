package AQS;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LatchTest {
    public static void AQSlatch(int threads, Runnable task) {
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(threads);
        for (int i = 0; i < threads; i++) {
            Thread t = new Thread() {
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + "i am ready!");
                        start.await();
                        task.run();
                        System.out.println(Thread.currentThread().getName() + "reach the finish");
                        end.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            };
            t.start();
        }
        try {
            TimeUnit.SECONDS.sleep((long) Math.random() * 1+1);

            long current = System.currentTimeMillis();
            System.out.println("match begin");
            start.countDown();

            end.await();

            System.out.println("match ends");
            System.out.println("total time :" + (System.currentTimeMillis() - current));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) {
        Thread mythread = new Thread() {
            public void run() {

                try {
                    System.out.println(Thread.currentThread().getName() + "i am running");
                    TimeUnit.SECONDS.sleep((long) Math.random() * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        AQSlatch(5, mythread);
    }
}
