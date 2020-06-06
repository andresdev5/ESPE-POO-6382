package ec.edu.espe.ajjacome2.distancecalculator.view;

import ec.edu.espe.ajjacome2.distancecalculator.util.Console;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Pattern;

/**
 *
 * @author jon_m
 */
public abstract class View {
    protected final HashMap<String, Object> templateVars;
    
    public View() {
        templateVars = new HashMap<>();
    }
    
    public void display() {
        Console.getInstance().echo(render());
    }
    
    public void set(String key, Object value) {
        templateVars.put(key, value);
    }
    
    private String render() {
        String rendered = template();
        
        for (Entry<String, Object> var : templateVars.entrySet()) {
            rendered = rendered.replaceAll(
                "\\$\\{\\s*" + Pattern.quote(var.getKey()) + "\\s*\\}",
                var.getValue().toString()
            );
        }
        
        return rendered;
    }
    
    protected abstract String template();
}
