import java.awt.*;

public class Hero {

    private Location loc;
    private int dir;
    private int size;
    private Color color;
    private int moves;

    public Hero(Location loc, int dir, int size, Color color) {
        this.loc = loc;
        this.dir = dir;
        this.size = size;
        this.color = color;
        moves = 0;
    }

    public Location getLoc() {
        return loc;
    }

    public int getDir() {
        return dir;
    }

    public Color getColor() {
        return color;
    }

    public int getMoves() {
        return moves;
    }

    public Rectangle getRect() {
        return getRect(loc.getX(), loc.getY(), 0, 0);
    }

    public Rectangle getRect(int relX, int relY, int offsetX, int offsetY) {
        return new Rectangle(relX * size + size + offsetX, relY * size + size + offsetY, size, size);
    }

    public void move(int key, char[][] maze) {
        int x = loc.getX();
        int y = loc.getY();
        switch(key) {
            case 38: // move forward
                switch(dir) {
                    case 0: // up
                        if (y > 0 && maze[y-1][x] == ' ') {
                            loc.changeY(-1);
                            moves++;
                        }
                        break;
                    case 1: // right
                        if (x < maze[y].length - 1 && maze[y][x + 1] == ' ') {
                            loc.changeX(1);
                            moves++;
                        }
                        break;
                    case 2: // down
                        if (y < maze.length - 1 && maze[y + 1][x] == ' ') {
                            loc.changeY(1);
                            moves++;
                        }
                        break;
                    case 3: // left
                        if (x > 0 && maze[y][x - 1] == ' ') {
                            loc.changeX(-1);
                            moves++;
                        }
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
