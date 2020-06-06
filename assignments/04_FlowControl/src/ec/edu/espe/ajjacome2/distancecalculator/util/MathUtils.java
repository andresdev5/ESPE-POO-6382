package ec.edu.espe.ajjacome2.distancecalculator.util;

import ec.edu.espe.ajjacome2.distancecalculator.model.Line;
import ec.edu.espe.ajjacome2.distancecalculator.model.Point;

/**
 *
 * @author jon_m
 */
public class MathUtils {
    public static class IndeterminatedResultException extends Exception {
        public IndeterminatedResultException() {
            super();
        }
        
        public IndeterminatedResultException(String message) {
            super(message);
        }
    }
    
    public static double distance2Points(Line line) {
        // √(x₂-x₁)² + (y₂-y₁)²
        return distance2Points(line.getPoint1(), line.getPoint2());
    }
    
    public static double distance2Points(Point point1, Point point2) {
        return Math.sqrt(
                Math.pow(point2.getX() - point1.getX(), 2) +
                Math.pow(point2.getY() - point1.getY(), 2));
    }
    
    public static double slopeLine(Line line) throws IndeterminatedResultException {
        return slopeLine(line.getPoint1(), line.getPoint2());
    }
    
    public static double slopeLine(Point point1, Point point2) throws IndeterminatedResultException {
        double denominator = (point2.getX() - point1.getX());
        
        if (denominator == 0) {
            throw new IndeterminatedResultException();
        }
        
        return (point2.getY() - point1.getY()) / denominator;
    }
}
