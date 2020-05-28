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
        eat();
        drink();
        poop();
        layAnEgg();
    }
    
    private void cluck() {
        System.out.printf("The chiken %s is clucking\n", name);
    }
    
    private void wander() {
        System.out.printf("The chiken %s is wandering\n", name);
    }
    
    private void eat() {
        System.out.printf(
            "The chiken %s of color %s and age %d is eating\n",
            name, color, age);
    }
    
    private void drink() {
        System.out.printf("The chiken %s is drinking\n", name);
    }
    
    private Poop poop() {
        Poop poopObject = new Poop();
        System.out.printf("The chiken %s is now pooping\n", name);
        return poopObject;
    }
    
    private Egg layAnEgg() {
        Egg egg = null;
        
        if (isMolting) {
            egg = new Egg();
            System.out.printf("The chiken %s is laying an egg\n", name);
        }
        
        return egg;
    }
}
