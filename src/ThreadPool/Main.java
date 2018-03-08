package ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws Exception {

        ExecutorService m=Executors.newCachedThreadPool();

        for(int i=1;i<=10;i++){
            final int count=i;

            m.submit(new Runnable(){
                @Override
                public void run() {
                    System.out.println("线程："+Thread.currentThread().getName()+"负责了"+count+"次任务");
                }

            });
            //下面这行代码注释的话，线程池会新建10个线程，不注释的话，因为会复用老线程，不会产生10个线程
         Thread.sleep(1);
        }

    }
}
