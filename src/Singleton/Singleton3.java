package Singleton;

import java.util.concurrent.TimeUnit;

public class Singleton3 {
    volatile private static Singleton3 instance =null;

    public static Singleton3 getInstance() {
        try {
            if(instance != null){//懒汉式

            }else{
                //创建实例之前可能会有一些准备性的耗时工作
                TimeUnit.MILLISECONDS.sleep(300);
                synchronized (Singleton3.class) {
                    if(instance == null){//二次检查
                        instance = new Singleton3();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return instance;
    }
}
