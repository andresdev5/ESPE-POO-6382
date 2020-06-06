package ec.edu.espe.ajjacome2.distancecalculator.model;

/**
 *
 * @author jon_m
 */
public class Line implements Model {
    public Point point1;
    public Point point2;
    
    public Line(Point point1, Point point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public Point getPoint1() {
        return point1;
    }

    public void setPoint1(Point point1) {
        this.point1 = point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public void setPoint2(Point point2) {
        this.point2 = point2;
    }
}
