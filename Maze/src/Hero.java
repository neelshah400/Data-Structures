import java.awt.*;

public class Hero {

    private Location loc;
    private int dir;
    private int size;
    Color color;

    public Hero(Location loc, int dir, int size, Color color) {
        this.loc = loc;
        this.dir = dir;
        this.size = size;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public Shape getRect() {
        int x = loc.getX();
        int y = loc.getY();
        Shape rect = new Rectangle(x * size + size, y * size + size, size, size);
        return rect;
    }

    public void move(int key, char[][] maze) {
        int x = loc.getX();
        int y = loc.getY();
        switch(key) {
            case 38: // move forward
                switch(dir) {
                    case 0: // up
                        if (y > 0 && maze[y-1][x] == ' ')
                            loc.changeY(-1);
                        break;
                    case 1: // right
                        if (x < maze[y].length - 1 && maze[y][x + 1] == ' ')
                            loc.changeX(1);
                        break;
                    case 2: // down
                        System.out.println(x + ", " + y);
                        if (y < maze.length - 1 && maze[y + 1][x] == ' ')
                            loc.changeY(1);
                        break;
                    case 3: // left
                        if (x > 0 && maze[y][x - 1] == ' ')
                            loc.changeX(-1);
                        break;
                    default:
                        break;
                }
                break;
            case 37: // rotate left
                dir--;
                if (dir < 0)
                    dir = 3;
                break;
            case 39: // rotate right
                dir++;
                if (dir > 3)
                    dir = 0;
                break;
            default:
                break;
        }
    }

}
