package ec.edu.espe.ajjacome2.distancecalculator.util;

import java.io.IOException;
import java.util.function.Function;
import org.fusesource.jansi.AnsiConsole;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp.Capability;

/**
 *
 * @author jon_m
 */
public class Console {
    private static Console instance;
    private final LineReader reader;
    private final Terminal terminal;

    public static Console getInstance() {
        if (instance == null) {
            try {
                instance = new Console();
            } catch (IOException exception) {
                System.err.print("Error while setup console");
                System.err.println(exception);
                System.exit(-1);
            }
        }
        
        return instance;
    }
    
    private Console() throws IOException {
        AnsiConsole.systemInstall();
        
        terminal = TerminalBuilder.builder()
                    .jansi(true)
                    .streams(System.in, System.out)
                    .system(true)
                    .build();
        
        reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .build();

        terminal.echo(false);
    }
    
    public Terminal getTerminal() {
        return terminal;
    }
    
    public LineReader getReader() {
        return reader;
    }
    
    public <T extends Object> T input(Class<T> type, String label) {
        Object value = new Object();
        
        while(true) {
            echo(label);
            
            try {
                String line = reader.readLine();
                
                if (type.isAssignableFrom(String.class)) {
                    value = line;
                }
                
                if (type.isAssignableFrom(Integer.class)) {
                    value = Integer.parseInt(line);
                }
                
                if (type.isAssignableFrom(Double.class)) {
                    value = Double.parseDouble(line);
                }
            } catch (UserInterruptException | EndOfFileException | NumberFormatException e) {
                continue;
            }
            
            break;
        }
        
        return (T) value;
    }
    
    public <T extends Object> T input(
        Class<T> type, String label, Function<T, Boolean> validation) {
        T value;
        boolean valid;
        
        do {
          value = input(type, label);
          valid = validation.apply(value);
        } while (!valid);
        
        return (T) value;
    }
    
    public String input(String label, Function<String, Boolean> validation) {
        return input(String.class, label, validation);
    }
    
    public String input(String label) {
        try {
            return reader.readLine(label);
        } catch (UserInterruptException exception) {
            return "";
        }
    }
    
    public String input() {
        try {
            return reader.readLine();
        } catch (UserInterruptException exception) {
            return "";
        }
    }
    
    public Console echo(String template, Object ...args) {
        terminal.writer().printf(template, args);
        return this;
    }
    
    public Console echo(Object content) {
        terminal.writer().print(content);
        return this;
    }
    
    public Console newLine() {
        return echo(System.lineSeparator());
    }
    
    public Console newLine(int repeat) {
        return echo(StringUtils.repeat(System.lineSeparator(), repeat));
    }

    public void clear() {
        terminal.puts(Capability.clear_screen);
        terminal.flush();
    }
}
