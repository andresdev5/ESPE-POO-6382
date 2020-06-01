/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.ajjacome2.moviecatalog.menu;

import java.util.function.Consumer;

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
