package ProducerConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Test {
    public static void main(String...args){
        //the length of the queue is 2
        BlockingQueue<String> q =new LinkedBlockingQueue<>(2);
        Producer producer=new Producer(q);
        Consumer consumer=new Consumer(q);
        for(int i=0;i<10;i++){
new Thread(producer,"i'm producing "+(i+1)).start();
new Thread(consumer,"i'm consuming "+(i+1)).start();
        }

    }
}
