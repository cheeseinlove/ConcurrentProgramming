package CasCounter;

public class MythreadB implements Runnable{
    private CasCounter counter;
    public MythreadB(CasCounter casCounter){
        this.counter=casCounter;
    }
    @Override
    public void run() {
        for(int i=0;i<5;i++)
        System.out.println("Thread B"+counter.increment());
    }
}
