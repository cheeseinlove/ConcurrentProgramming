package ProducerConsumer;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
    private BlockingQueue<String> queue;
    public  Producer(BlockingQueue<String> queue){
        this.queue=queue;
    }

    @Override
    public void run() {
//        String temp="Product :"+Thread.currentThread().getName();
//
//        System.out.println("Producer :" + Thread.currentThread().getName());
//        queue.add(temp);

            try {
                String temp="Product :"+Thread.currentThread().getName();

                System.out.println("Producer :" + Thread.currentThread().getName());
                queue.put(temp);
            } catch (InterruptedException e) {
                e.printStackTrace();

        }
    }
}
