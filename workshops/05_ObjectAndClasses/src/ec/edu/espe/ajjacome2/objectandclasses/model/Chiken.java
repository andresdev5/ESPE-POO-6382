package ec.edu.espe.ajjacome2.objectandclasses.model;

/**
 *
 * @author jon_m
 */
public class Chiken {
    private String name;
    private String color;
    private int age;
    private boolean isMolting;
    
    public void doStuff() {
        cluck();
        wander();
    }
    
    private void cluck() {
        System.out.printf("The chiken %s is clucking\n", name);
    }
    
    private void wander() {
        System.out.printf("The chiken %s is wandering\n", name);
    }
    
    private void eat() {}
    private void drink() {}
    private void poop() {}
    private void layAnEgg() {}
}
