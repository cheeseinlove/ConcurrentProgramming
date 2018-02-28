package CasCounter;

public class CasCounter {
    private SimulatedCAS value;
    public int getValue(){
        return value.get();
    }
    public int increment(){
        int v;
        do{
            v=value.get();
        }while (v!=value.compareAndSwap(v,v+1));
        return v+1;
    }
    public static void main(String...args){
        //对同一资源的并发访问，读改写操作
        CasCounter counter=new CasCounter();
        counter.value=new SimulatedCAS();
        counter.value.compareAndSet(0,5);

        MythreadA a=new MythreadA(counter);
        MythreadB b=new MythreadB(counter);
a.run();
b.run();
    }
}
