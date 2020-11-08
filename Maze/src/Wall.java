import java.awt.*;

public class Wall {

    private String name;
    private int[] xCoordinates;
    private int[] yCoordinates;
    private int size;
    private int fov;
    private Boolean filled;
    private Color specialColor;

    public Wall(String name, int[] xCoordinates, int[] yCoordinates, int size, int fov, Boolean filled) {
        this.name = name;
        this.xCoordinates = xCoordinates;
        this.yCoordinates = yCoordinates;
        this.size = size;
        this.fov = fov;
        this.filled = filled;
    }

    public Wall(String name, int[] xCoordinates, int[] yCoordinates, int size, int fov, Boolean filled, Color specialColor) {
        this(name, xCoordinates, yCoordinates, size, fov, filled);
        this.specialColor = specialColor;
    }

    public Boolean isFilled() {
        return filled;
    }

    public Polygon getPoly() {
        return new Polygon(xCoordinates, yCoordinates, xCoordinates.length);
    }

    public Color getSpecialColor() {
        return specialColor;
    }

    public GradientPaint getPaint() {
        int factor = (int) (size * 0.8);
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
