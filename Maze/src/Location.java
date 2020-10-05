public class Location {

    private int x;
    private int y;

    public Location (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void changeX(int delta) {
        x += delta;
    }

    public int getY() {
        return y;
    }

    public void changeY(int delta) {
        y += delta;
    }

}
