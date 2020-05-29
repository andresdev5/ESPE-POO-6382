package ec.edu.espe.ajjacome2.objectandclasses.model;

/**
 *
 * @author jon_m
 */
public class Chiken {
    private int id;
    private String name;
    private String color;
    private int age;
    private boolean isMolting;
    
    public Chiken(
        int id, String name, String color, int age, boolean isMolting) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.age = age;
        this.isMolting  = isMolting;
    }
    
    public void doStuff(int forTime) {
        cluck();
        wander();
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
            egg = new Egg(1);
            System.out.printf("The chiken %s is laying an egg\n", name);
        }
        
        return egg;
    }

    @Override
    public String toString() {
        return "Chiken{" + "id=" + id + ", name=" + name + ", color=" + color + 
               ", age=" + age + ", isMolting=" + isMolting + '}';
    }
}
