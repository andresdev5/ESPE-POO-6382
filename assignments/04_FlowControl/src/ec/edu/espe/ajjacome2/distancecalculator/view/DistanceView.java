package ec.edu.espe.ajjacome2.distancecalculator.view;

import static org.fusesource.jansi.Ansi.ansi;

/**
 *
 * @author jon_m
 */
public class DistanceView extends View {    
    @Override
    public String template() {
        return String.join(
            "\n",
            "Distance: " + ansi().fgBrightGreen().a("${distance}").bold().reset()
        );
    }
}
