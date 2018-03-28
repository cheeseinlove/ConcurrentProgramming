package ObserverPattern.NotThreadSafe;

public class Main {
    public static void main (String[] args) {
        // Create the zoo to store animals
        Zoo zoo = new Zoo();
        // Register a listener to be notified when an animal is added
        zoo.registerAnimalAddedListener(new PrintNameAnimalAddedListener());
        zoo.registerAnimalAddedListener(new CountingAnimalAddedListener());
        zoo.registerAnimalAddedListener(new AnimalAddedListener() {
            @Override
            public void updateAnimalAdded(Animal animal) {
                System.out.println("Welcome "+animal.getName()+"!");
            }
        });
         AnimalAddedListener l4=zoo.registerAnimalAddedListener((animal -> System.out.println("give some applauses!")));
//        AnimalAddedListener l2=new PrintNameAnimalAddedListener();
//        zoo.registerAnimalAddedListener(l2);
        // Add an animal notify the registered listeners
        zoo.addAnimal(new Animal("Tiger"));
        zoo.addAnimal(new Animal("Cat"));

    }
}
