package ec.edu.espe.ajjacome2.moviecatalog.common;

import java.util.HashMap;

/**
 * Class to display a view from a Model
 * 
 * @author jon_m
 */
public abstract class BaseView<T extends Model> {
    /**
     * Data map that contains template variables
     */
    protected HashMap<String, Object> data = new HashMap<>();
    
    /**
     * Model instance
     */
    protected T context;
    
    public BaseView(T context) {
        this.context = context;
        init();
    }
    
    public final String render() {
        return render(getTemplate());
    }
    
    /**
     * render a template with a custom string
     * 
     * @param template
     * @return 
     */
    public final String render(String template) {
        String rendered = template;
        
        for (HashMap.Entry<String, Object> entry : data.entrySet()) {
            Object value = entry.getValue();
            
            if (value == null) {
                continue;
            }
            
            rendered = rendered.replaceAll(
                "\\$\\{\\s*" + entry.getKey() + "\\s*\\}",
                value.toString()
            );
        }
        
        return rendered;
    }
    
    public final void display() {
        System.out.print(render());
    }
    
    /**
     * Display a template string
     * 
     * @param template 
     */
    public final void display(String template) {
        System.out.print(render(template));
    }
    
    protected abstract void init();
    protected abstract String getTemplate();
}
