package ObserverPattern.ThreadSafe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadSafeZoo {
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    protected final Lock readLock = readWriteLock.readLock();
    protected final Lock writeLock = readWriteLock.writeLock();
    private List<Animal> animals = new ArrayList<>();
    private List<AnimalAddedListener> listeners = new ArrayList<>();
    public void addAnimal (Animal animal) {
        // Add the animal to the list of animals
        this.animals.add(animal);
        // Notify the list of registered listeners
        this.notifyAnimalAddedListeners(animal);
    }
    public AnimalAddedListener registerAnimalAddedListener (AnimalAddedListener listener) {
        // Lock the list of listeners for writing
        this.writeLock.lock();
        try {
            // Add the listener to the list of registered listeners
            this.listeners.add(listener);
        }
        finally {
            // Unlock the writer lock
            this.writeLock.unlock();
        }
        return listener;
    }
    public void unregisterAnimalAddedListener (AnimalAddedListener listener) {
        // Lock the list of listeners for writing
        this.writeLock.lock();
        try {
            // Remove the listener from the list of the registered listeners
            this.listeners.remove(listener);
        }
        finally {
            // Unlock the writer lock
            this.writeLock.unlock();
        }
    }
    public void notifyAnimalAddedListeners (Animal animals) {
        // Lock the list of listeners for reading
        this.readLock.lock();
        try {
            // Notify each of the listeners in the list of registered listeners
            this.listeners.forEach(listener -> listener.updateAnimalAdded(animals));
        }
        finally {
            // Unlock the reader lock
            this.readLock.unlock();
        }
    }
}
