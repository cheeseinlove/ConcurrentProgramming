package Singleton;

public class test extends Thread {

    public static void main(String... args) {
//        for (int i = 0; i < 10; i++) {
//            new Thread(new test(), "" + i).start();
//        }

//cglib代理单例
        CglibProxy proxy = new CglibProxy(Singleton3.getInstance());
        Singleton3 s1 =  (Singleton3) proxy.Proxy();

        s1.doSomething();
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
