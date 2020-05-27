package ec.edu.espe.ajjacome2.objectandclasses;

import ec.edu.espe.ajjacome2.objectandclasses.model.Coords;
import ec.edu.espe.ajjacome2.objectandclasses.model.Panda;

/**
 *
 * @author jon_m
 */
public class Main {
    public static void main(String[] args) {
        Panda genma = new Panda("genma", 8);
        genma.wakeUp();
        genma.eat(Panda.Food.Bamboo, 4);
        genma.move(new Coords(10, 5));
        genma.move(new Coords(5, 2));
        genma.sleep();
    }
}
