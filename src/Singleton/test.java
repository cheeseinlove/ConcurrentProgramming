package Singleton;

public class test extends Thread {

    public static void main(String... args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new test(), "" + i).start();
        }


    }

    @Override
    public void run() {
        //枚举
//        System.out.println(Singleton1.INSTANCE.hashCode());
        //饿汉
//        System.out.println(Singleton2.getInstance().hashCode());
        //DCL
        System.out.println(Singleton3.getInstance().hashCode());


    }
}
