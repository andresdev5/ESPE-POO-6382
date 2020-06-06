package ec.edu.espe.ajjacome2.distancecalculator.util;

/**
 *
 * @author jon_m
 */
public class ConsoleMenuOption {
    private String label;
    private Runnable callback;
    private boolean await;
    
    public ConsoleMenuOption(String label, Runnable callback) {
        this.label = label;
        this.callback = callback;
        this.await = true;
    }
    
    public ConsoleMenuOption(String label, Runnable callback, boolean await) {
        this.label = label;
        this.callback = callback;
        this.await = await;
    }
    
    public void execute() {
        callback.run();
    }

    public String getLabel() {
        return label;
    }
    
    public boolean mustAwait() {
        return await;
    }
}
