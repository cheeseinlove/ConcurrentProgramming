package CasCounter;

public class SimulatedCAS {
    private int value;
    public synchronized int get(){return value;}
    public synchronized int compareAndSwap(int expect,int newValue){
        int old=value;
        if(old==expect)
            value=newValue;
        return old;
    }
    public synchronized boolean compareAndSet(int expect,int newValue){
        return (expect==compareAndSwap(expect, newValue));
    }
}
