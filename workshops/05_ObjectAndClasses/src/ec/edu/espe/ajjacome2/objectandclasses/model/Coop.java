package ec.edu.espe.ajjacome2.objectandclasses.model;

/**
 *
 * @author jon_m
 */
public class Coop {
    private int id;
    private Chiken Chikens[];

    public Coop(int id, Chiken[] Chikens) {
        this.id = id;
        this.Chikens = Chikens;
    }
    
    public void add(Chiken chicken) {
        
    }
    
    public void remove(int chikenId) {
        
    }
    
    public void resetIteration() {
        
    }
    
    public Chiken next() {
        return new Chiken(1, "Susy", "Silver", 2, false);
    }
}
