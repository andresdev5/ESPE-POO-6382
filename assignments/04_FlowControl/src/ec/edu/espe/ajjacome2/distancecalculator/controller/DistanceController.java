package ec.edu.espe.ajjacome2.distancecalculator.controller;

import ec.edu.espe.ajjacome2.distancecalculator.model.Line;
import ec.edu.espe.ajjacome2.distancecalculator.model.Point;
import ec.edu.espe.ajjacome2.distancecalculator.util.Console;
import ec.edu.espe.ajjacome2.distancecalculator.util.MathUtils;
import ec.edu.espe.ajjacome2.distancecalculator.view.DistanceView;
import ec.edu.espe.ajjacome2.distancecalculator.view.GraphView;
import ec.edu.espe.ajjacome2.distancecalculator.view.SlopeView;
import java.awt.event.WindowAdapter;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jon_m
 */
public class DistanceController {
    private final Console console;
    private final AtomicBoolean graphOpened;
    
    public DistanceController() {
        this.console = Console.getInstance();
        graphOpened = new AtomicBoolean(false);
    }
    
    public void calculateTwoPointsDistance() {
        double x1 = console.input(Double.class, "insert X₁: ");
        double y1 = console.input(Double.class, "insert Y₁: ");
        double x2 = console.input(Double.class, "insert x₂: ");
        double y2 = console.input(Double.class, "insert Y₂: ");
        
        Point point1 = new Point(x1, y1);
        Point point2 = new Point(x2, y2);
        DistanceView view = new DistanceView();
        
        view.set("distance", MathUtils.distance2Points(point1, point2));
        console.newLine(2);
        view.display();
        console.newLine(2);
        
        String confirm = console.input("Do you want see an graphic? [y/n]: ", (value) -> {
            return (value.trim().toLowerCase().matches("^y|n$"));
        });
        
        if (confirm.equals("y")) {
            displayGraphLine(new Line(point1, point2));
        }
    }
    
    public void calculateSlopeLine() {
        double x1 = console.input(Double.class, "insert X₁: ");
        double y1 = console.input(Double.class, "insert Y₁: ");
        double x2 = console.input(Double.class, "insert x₂: ");
        double y2 = console.input(Double.class, "insert Y₂: ");
        
        Point point1 = new Point(x1, y1);
        Point point2 = new Point(x2, y2);
        SlopeView view = new SlopeView();
        
        try {
            view.set("slope", MathUtils.slopeLine(new Line(point1, point2)));
        } catch (MathUtils.IndeterminatedResultException ex) {
            view.set("slope", "indeterminated");
        }
        
        console.newLine(2);
        view.display();
        console.newLine(2);
        
        String confirm = console.input("Do you want see an graphic? [y/n]: ", (value) -> {
            return (value.trim().toLowerCase().matches("^y|n$"));
        });
        
        if (confirm.equals("y")) {
            displayGraphLine(new Line(point1, point2));
        }
    }
    
    private void displayGraphLine(Line line) {
        GraphView graph = new GraphView();
        
        graph.getGraphPanel().drawLine(line);
        graph.setDefaultCloseOperation(GraphView.DISPOSE_ON_CLOSE);
        graph.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                console.getTerminal().resume();
                graphOpened.set(false);
            }
        });
        graph.setVisible(true);
        graph.setAlwaysOnTop(true);
        graph.toFront();
        
        graphOpened.set(true);
        console.getTerminal().pause();

        while (graphOpened.get()) {}
    }
}
