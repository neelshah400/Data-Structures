import java.awt.geom.Rectangle2D;

public class Block {

    private int x;
    private int y;
    private int width;
    private int height;

    public Block(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int change) {
        x += change;
        if (x <= -1 * width)
            x += 90 * width;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(x, y, width, height);
    }

}
