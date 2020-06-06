package ec.edu.espe.ajjacome2.distancecalculator.model;

/**
 *
 * @author jon_m
 */
public class Point implements Model {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }
}
