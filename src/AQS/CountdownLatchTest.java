package AQS;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountdownLatchTest {

    public static void main(String[] args) {

ExecutorService service = Executors.newCachedThreadPool();
//构造一个用给定计数初始化的 CountDownLatch,相当于裁判的口哨
final CountDownLatch cdOrder = new CountDownLatch(1);

//相当于定义3个运行员
        final CountDownLatch cdAnswer = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        System.out.println("线程"
                                + Thread.currentThread().getName() + "正准备接受命令");

                        // 等待发令枪
                        cdOrder.await();//使当前线程在锁存器倒计数至零之前一直等待

                        System.out.println("线程"
                                + Thread.currentThread().getName() + "已接受命令");
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out
                                .println("线程"
                                        + Thread.currentThread().getName()
                                        + "回应命令处理结果");

                        // 各个运动员完报告成绩之后，通知裁判
                        cdAnswer.countDown();//递减锁存器的计数，如果计数到达零，则释放所有等待的线程

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            service.execute(runnable);
        }
        try {
           Thread.sleep((long) (Math.random() * 10000));

            System.out.println("线程" + Thread.currentThread().getName()
                    + "即将发布命令");
            // 发令枪打响，比赛开始
            cdOrder.countDown();

            System.out.println("线程" + Thread.currentThread().getName()
                    + "已发送命令，正在等待结果");

            // 裁判等待各个运动员的结果
            cdAnswer.await();

            // 裁判公布获得所有运动员的成绩
            System.out.println("线程" + Thread.currentThread().getName()
                    + "已收到所有响应结果");
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.shutdown();

    }
}
