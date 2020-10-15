import java.awt.*;

public class Wall {

    String name;
    private int[] xCoordinates;
    private int[] yCoordinates;
    int size;
    int fov;
    Boolean filled;

    public Wall(String name, int[] xCoordinates, int[] yCoordinates, int size, int fov, Boolean filled) {
        this.name = name;
        this.xCoordinates = xCoordinates;
        this.yCoordinates = yCoordinates;
        this.size = size;
        this.fov = fov;
        this.filled = filled;
    }

    public Boolean isFilled() {
        return filled;
    }

    public Polygon getPoly() {
        return new Polygon(xCoordinates, yCoordinates, xCoordinates.length);
    }

    public GradientPaint getPaint() {
        int factor = size / 2;
        int start = 205;
        int end = 205;
        if (filled) {
            start -= factor * fov;
            end -= factor * (fov + 1);
        }
        else {
            start = 0;
            end = 0;
        }
        if (name.equals("top trapezoid") || name.equals("bottom trapezoid"))
            return new GradientPaint(xCoordinates[0], yCoordinates[0], new Color(start, start, start), xCoordinates[0], yCoordinates[3], new Color(end, end, end));
        return new GradientPaint(xCoordinates[0], yCoordinates[0], new Color(start, start, start), xCoordinates[1], yCoordinates[0], new Color(end, end, end));
    }

}
