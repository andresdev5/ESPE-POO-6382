package ec.edu.espe.ajjacome2.objectandclasses.view;

import ec.edu.espe.ajjacome2.objectandclasses.model.Chiken;
import ec.edu.espe.ajjacome2.objectandclasses.utils.FileManager;

/**
 *
 * @author jon_m
 */
public class FarmSystem {
    public static void main(String[] args) {
        FileManager fileManager = new FileManager();
        Chiken chicken = new Chiken(1, "Lucy", "white", 2, false);
        String fileName = "chickens.csv";
        
        System.out.println("Jacome Andres ---> 08ExceptionsAndDependency");
        System.out.println(chicken);
        
        fileManager.create(fileName);
        fileManager.write(chicken.generateCSVData(),fileName);
        System.out.println(fileManager.read(fileName));
        
        // chicken.id = 2;
        // chicken.name = "Lucia";
        
        chicken.setId(2);
        chicken.setName("Lucia");
        
        System.out.println(chicken.getName());
    }
}
