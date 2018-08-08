package CasCounter;

import java.util.concurrent.atomic.AtomicReference;

public class ConcurrentLinkedQueue<E> {
    private static class Node<E> {
        public final E item;
        public final AtomicReference<Node<E>> next;

        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = new AtomicReference<>(next);
        }
    }

    private final Node<E> dummy = new Node<E>(null, null);
    private final AtomicReference<Node<E>> head = new AtomicReference<>(dummy);
    private final AtomicReference<Node<E>> tail = new AtomicReference<>(dummy);

    public boolean put(E item) {
        Node<E> newH = new Node<E>(item, null);
        while (true) {
            Node<E> curTail = tail.get();
            Node<E> tailNext = curTail.next.get();
            if (curTail == tail.get()) {
                //有线程在插入处于中间态，帮助移动尾指针
                if (tailNext != null)
                    tail.compareAndSet(curTail, tailNext);
                else {
                    if (curTail.next.compareAndSet(null, newH)) {
                        tail.compareAndSet(curTail, newH);
                        return true;
                    }
                }
            }


        }


    }


}
