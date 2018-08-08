package CasCounter;

import java.util.concurrent.atomic.AtomicReference;

public class CounCurrentStack<E> {
    AtomicReference<Node<E>> top = new AtomicReference<Node<E>>();

    private static class Node<E> {
        public final E item;
        public Node<E> next;

        public Node(E item) {
            this.item = item;
        }
    }

    public void push(E item) {
        Node<E> newH = new Node<E>(item);
        Node<E> oldH;
        do {
            oldH = top.get();
            newH.next = oldH;
        } while (!top.compareAndSet(oldH, newH));

    }

    public E pop() {
        Node<E> newh;
        Node<E> oldH;
        do {
            newh = top.get().next;
            if (newh == null)
                return null;
            oldH = top.get();
        } while (!top.compareAndSet(oldH, newh));
        return oldH.item;


    }
}
