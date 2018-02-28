package CasCounter;

public class MythreadA implements Runnable{
    private CasCounter counter;
    public MythreadA(CasCounter casCounter){
        this.counter=casCounter;
    }
    @Override
    public void run() {
        for(int i=0;i<5;i++)
            System.out.println("Thread A"+counter.increment());
    }
}
