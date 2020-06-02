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
        System.out.printf("The chiken %s is clucking\n", getName());
    }
    
    private void wander() {
        System.out.printf("The chiken %s is wandering\n", getName());
    }
    
    private void eat() {
        System.out.printf("The chiken %s of color %s and age %d is eating\n", getName(), getColor(), getAge());
    }
    
    private void drink() {
        System.out.printf("The chiken %s is drinking\n", getName());
    }
    
    private Poop poop() {
        Poop poopObject = new Poop();
        System.out.printf("The chiken %s is now pooping\n", getName());
        return poopObject;
    }
    
    private Egg layAnEgg() {
        Egg egg = null;
        
        if (isIsMolting()) {
            egg = new Egg(1);
            System.out.printf("The chiken %s is laying an egg\n", getName());
        }
        
        return egg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isIsMolting() {
        return isMolting;
    }

    public void setIsMolting(boolean isMolting) {
        this.isMolting = isMolting;
    }
    
    public String generateCSVData() {
        return getId() + "," + getName() + "," + getColor() + "," + getAge() + "," + isIsMolting();
    }

    @Override
    public String toString() {
        return "Chiken{" + "id=" + getId() + ", name=" + getName() + ", color=" + getColor() + 
               ", age=" + getAge() + ", isMolting=" + isIsMolting() + '}';
    }
}
