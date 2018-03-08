package ThreadJoin;

public class AThread extends Thread {
    BThread    bt;
    public AThread(BThread    bt){
        super("[AThread] Thread");
        this.bt=bt;
    }
    @Override
    public void run(){
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " start.");
        try {
            bt.join();
            System.out.println(threadName + " end.");
        } catch (Exception e) {
            System.out.println("Exception from " + threadName + ".run");
        }
    }

}
