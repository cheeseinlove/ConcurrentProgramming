/**
 * 两个线程计数计数直到100
 *
 * @author LX
 * @date 2018/7/24
 **/
public class Count100 {
    private static int count = 1;
    private static Object lock = new Object();

    private static class ThreadA implements Runnable {

        @Override
        public void run() {

            while (count < 100)
                synchronized (lock) {
                    while (count % 2 == 0)
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    System.out.println(count++);
                    lock.notifyAll();
                }
        }
    }

    private static class ThreadB implements Runnable {

        @Override
        public void run() {
            while (count < 100)
                synchronized (lock) {
                    while (count % 2 == 1)
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    System.out.println(count++);
                    lock.notifyAll();
                }
        }
    }

    public static void main(String... agrs) throws InterruptedException {
        Thread a = new Thread(new ThreadA());
        Thread b = new Thread(new ThreadB());

        a.start();
        b.start();


    }
}
