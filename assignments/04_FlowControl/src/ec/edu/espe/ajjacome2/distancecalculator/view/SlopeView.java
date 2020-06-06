package ec.edu.espe.ajjacome2.distancecalculator.view;

import static org.fusesource.jansi.Ansi.ansi;

/**
 *
 * @author jon_m
 */
public class SlopeView extends View {    
    @Override
    public String template() {
        return String.join(
            "\n",
            "Slope: " + ansi().fgBrightGreen().a("${slope}").bold().reset()
        );
    }
}
