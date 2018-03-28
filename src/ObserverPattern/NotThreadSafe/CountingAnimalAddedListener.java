package ObserverPattern.NotThreadSafe;

public class CountingAnimalAddedListener implements AnimalAddedListener {
    private static int animalsAddedCount = 0;
    @Override
    public void updateAnimalAdded(Animal animal) {
        // Increment the number of animals
        animalsAddedCount++;
        // Print the number of animals
        System.out.println("Total animals added: " + animalsAddedCount);
    }
}
