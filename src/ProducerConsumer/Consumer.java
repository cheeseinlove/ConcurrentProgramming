package ProducerConsumer;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable{
    private  BlockingQueue<String> queue;
    public  Consumer(BlockingQueue<String> queue){
        this.queue=queue;
    }

    @Override
    public void run() {


            try {
                String temp=queue.take();
                System.out.println("Consumer :" + temp);


            } catch (InterruptedException e) {
                e.printStackTrace();

        }
    }
}
