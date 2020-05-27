package ec.edu.espe.ajjacome2.objectandclasses.model;

/**
 * 
 * @author jon_m
 */
public class Panda {
    public static enum Food {
        Bamboo("bambú"),
        Insect("insecto"),
        Leaf("Hoja");
        
        private String name;
        
        Food(String name) {
            this.name = name;
        }
        
        @Override public String toString() {
            return name;
        }
    };
    
    private String name;
    private int age;
    boolean isSleeping;
    Coords position;
    
    /**
     * Create a new Panda instance
     * 
     * @param name
     * @param age 
     */
    public Panda(String name, int age) {
        this.name = name;
        this.age = age;
        this.isSleeping = true;
        this.position = new Coords(0, 0);
        
        System.out.printf("%s is now created!\n", name);
    }
    
    /**
     * Feed the panda with a certain amount of food
     * 
     * @param food Food instance
     * @param amount
     */
    public void eat(Food food, int amount) {
        if (amount <= 0) {
            return;
        }
        
        System.out.printf(
            "%s is now eating %d '%s%s'\n", 
            name, amount, food, (amount > 1 ? "s" : ""));
    }
    
    public void sleep() {
        if (this.isSleeping) {
            System.out.printf("%s is already sleeping\n", name);
        } else {
            this.isSleeping = true;
            System.out.printf("%s is now sleeping\n", name);
        }
    }
    
    public void wakeUp() {
        if (!this.isSleeping) {
            System.out.printf("%s is already awake\n", name);
        } else {
            this.isSleeping = false;
            System.out.printf("%s is awake now\n", name);
        }
    }
    
    /**
     * Move the current position of panda with given coords
     * 
     * @param coords coords to add to the current coords
     */
    public void move(Coords coords) {
        int x = position.getX() + coords.getX();
        int y = position.getY() + coords.getY();
        
        setPosition(new Coords(x, y));
        
        System.out.printf(
            "%s is now at position (%d, %d)\n",
            name, position.getX(), position.getY());
    }
    
    public Coords getPosition() {
        return position;
    }
    
    public final void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public final void setAge(int age) {
        if (age < 0 || age > 21) {
            throw new IllegalArgumentException(String.format(
                "Expecte value between 1 and 21, %d given", age));
        }
        
        this.age = age;
    }
    
    public int getAge() {
        return age;
    }
    
    private void setPosition(Coords position) {
        this.position = position;
    }
}
