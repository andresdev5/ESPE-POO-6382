package ec.edu.espe.ajjacome2.distancecalculator;

import ec.edu.espe.ajjacome2.distancecalculator.controller.DistanceController;
import ec.edu.espe.ajjacome2.distancecalculator.util.ConsoleMenu;
import ec.edu.espe.ajjacome2.distancecalculator.util.ConsoleMenuOption;
import java.io.IOException;

/**
 *
 * @author jon_m
 */
public class DistanceCalculator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        ConsoleMenu menu = new ConsoleMenu("Distance between 2 points");
        DistanceController controller = new DistanceController();
        
        menu.addOption(new ConsoleMenuOption(
            "Calculate distance between 2 points",
            controller::calculateTwoPointsDistance
        ));
        
        menu.addOption(new ConsoleMenuOption(
            "Calculate slope line",
            controller::calculateSlopeLine
        ));
        
        menu.addOption(new ConsoleMenuOption(
            "Exit",
            menu::exit,
            false
        ));
        
        menu.display("Andres Jacome - 04 Flow Control\n\n");
    }
}
