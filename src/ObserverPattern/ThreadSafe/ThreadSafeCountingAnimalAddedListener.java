package ObserverPattern.ThreadSafe;

import java.util.concurrent.atomic.AtomicLong;

class ThreadSafeCountingAnimalAddedListener implements AnimalAddedListener {
    private static AtomicLong animalsAddedCount = new AtomicLong(0);
    @Override
    public void updateAnimalAdded (Animal animal) {
        // Increment the number of animals
        animalsAddedCount.incrementAndGet();
        // Print the number of animals
        System.out.println("Total animals added: " + animalsAddedCount);
    }
}