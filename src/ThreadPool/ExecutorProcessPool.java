package ThreadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 线程处理类
 *@author I346123
 *@date 2018.3.6
 */
public class ExecutorProcessPool {

    private ExecutorService executor;
    private static ExecutorProcessPool pool = new ExecutorProcessPool();
    private final int threadMax = 10;

    private ExecutorProcessPool() {
        System.out.println("threadMax>>>>>>>" + threadMax);
        executor = ExecutorServiceFactory.getInstance().createFixedThreadPool(threadMax);
    }

    public static ExecutorProcessPool getInstance() {
        return pool;
    }

    /**
     * 关闭线程池，这里要说明的是：调用关闭线程池方法后，线程池会执行完队列中的所有任务才退出
     *
     */
    public void shutdown(){
        executor.shutdown();
    }

    /**
     * 提交任务到线程池，可以接收线程返回值
     *
     * @param task
     * @return
     */
    public Future<?> submit(Runnable task) {
        return executor.submit(task);
    }

    /**
     * 提交任务到线程池，可以接收线程返回值
     *
     * @param task
     * @return
     */
    public Future<?> submit(Callable<?> task) {
        return executor.submit(task);
    }

    /**
     * 直接提交任务到线程池，无返回值
     *
     * @param task
     */
    public void execute(Runnable task){
        executor.execute(task);
    }

}