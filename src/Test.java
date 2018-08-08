public class Test {
    private static class t1 extends Thread {
        Test t1;

        public t1(Test t) {
            this.t1 = t;
        }

        @Override
        public void run() {
            System.out.println(t1.hashCode());
            t1.hello();
            System.out.println("1 end");

        }
    }

    private static class t2 extends Thread {
        Test t2;

        public t2(Test t) {
            this.t2 = t;
        }

        @Override
        public void run() {
            System.out.println(t2.hashCode());
            t2.bye();
            System.out.println("2 end");

        }
    }

    public static void main(String... args) {
        Test t = new Test();
        Thread a = new Thread(new t1(t));
        Thread b = new Thread(new t2(t));
        a.start();
        b.start();
    }

    public  void hello() {
        synchronized (this){
            System.out.println("hello");
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public   synchronized void bye() {
        System.out.println("bye");
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
