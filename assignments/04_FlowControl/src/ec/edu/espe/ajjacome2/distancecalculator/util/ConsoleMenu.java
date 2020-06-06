package ec.edu.espe.ajjacome2.distancecalculator.util;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.fusesource.jansi.Ansi.ansi;

/**
 *
 * @author jon_m
 */
public class ConsoleMenu {
    private final Console console;
    private final String title;
    private final AtomicBoolean isRunning;
    private final ArrayList<ConsoleMenuOption> options;
    
    public ConsoleMenu(String title) {
        this.title = title;
        console = Console.getInstance();
        isRunning = new AtomicBoolean(true);
        options = new ArrayList<>();
    }
    
    public void addOption(ConsoleMenuOption option) {
        options.add(option);
    }
    
    public void display() {
        display("");
    }
    
    public void display(String headerPrefix) {
        StringBuilder header = new StringBuilder();
        String line;
        int consoleWidth = console.getTerminal().getWidth();
        int index = 0;
        
        header.append(headerPrefix);
        header.append(StringUtils.repeat(
                "-", (consoleWidth / 2) - (title.length() / 2) - 3));
        header.append("| ");
        header.append(ansi().bold().bgMagenta().a(title).reset());
        header.append(" |");
        header.append(StringUtils.repeat(
                "-", (consoleWidth / 2) - (title.length() / 2) - 3));
        header.append(System.lineSeparator());
            
        for (ConsoleMenuOption option : options) {
            header.append(String.format(
                "%s: %s",
                ansi().bold().fgCyan().a(Integer.toString(index + 1)).reset(),
                option.getLabel()
            ));
            header.append(System.lineSeparator());
            index++;
        }

        header.append(StringUtils.repeat("-", consoleWidth - 2));
        header.append(System.lineSeparator());
        
        while(isRunning.get()) {
            console.clear();
            console.echo(header);
            console.newLine(2);
            
            line = console.input(
                ansi()
                .bold()
                .bgBrightCyan()
                .a("Choose an option: ").reset().toString());
            line = line.trim();
            
            int optionNumber;
            boolean isValidOption;
            ConsoleMenuOption option = null;
            
            try {
                optionNumber = Integer.parseInt(line);
            } catch (NumberFormatException ex) {
                optionNumber = 0;
            }
            
            isValidOption = !(optionNumber <= 0 || optionNumber > options.size());
            
            if (!isValidOption) {
                console
                    .newLine()
                    .echo(ansi().bold().fgRed().a("Invalid option").reset());
            } else {
                console.newLine(2);
                option = options.get(optionNumber - 1);
                option.execute();
            }

            if (!isValidOption || (option != null && option.mustAwait())) {
                console
                    .newLine(3)
                    .echo("Press <enter> to continue...")
                    .input();
            }
        }
    }
    
    public void exit() {
        isRunning.set(false);
    }
}
