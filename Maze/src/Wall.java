import java.awt.*;
import java.util.Arrays;

public class Wall {

    private int[] xCoordinates;
    private int[] yCoordinates;
    Color color;
    Boolean filled;

    public Wall(int[] xCoordinates, int[] yCoordinates, Color color, Boolean filled) {
        this.xCoordinates = xCoordinates;
        this.yCoordinates = yCoordinates;
        this.color = color;
        this.filled = filled;
    }

    public Color getColor() {
        return color;
    }

    public Boolean isFilled() {
        return filled;
    }

    public Polygon getPoly() {
        return new Polygon(xCoordinates, yCoordinates, xCoordinates.length);
    }

    public String toString() {
        return "Wall{" +
                "xCoordinates=" + Arrays.toString(xCoordinates) +
                ", yCoordinates=" + Arrays.toString(yCoordinates) +
                ", color=" + color +
                '}';
    }

}
