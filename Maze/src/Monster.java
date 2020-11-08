import java.awt.*;
import java.util.ArrayList;

public class Monster {

    private Location loc;
    private int dir;
    private int size;
    private Color color;
    private int speed;

    public Monster(Location loc, int dir, int size, Color color) {
        this.loc = loc;
        this.dir = dir;
        this.size = size;
        this.color = color;
        speed = (int)(Math.random() * 700) + 100;
    }

    public Location getLoc() {
        return loc;
    }

    public Color getColor() {
        return color;
    }

    public int getSpeed() {
        return speed;
    }

    public Rectangle getRect() {
        return getRect(loc.getX(), loc.getY(), 0, 0);
    }

    public Rectangle getRect(int relX, int relY, int offsetX, int offsetY) {
        return new Rectangle(relX * size + size + offsetX, relY * size + size + offsetY, size, size);
    }

    public void move(char[][] maze) {

        int x = loc.getX();
        int y = loc.getY();

        boolean canMoveForward = false;
        boolean canRotateLeft = false;
        boolean canRotateRight = false;

        switch(dir) {
            case 0: // up
                if (y > 0 && maze[y - 1][x] == ' ')
                    canMoveForward = true;
                if (x > 0 && maze[y][x - 1] == ' ')
                    canRotateLeft = true;
                if (x < maze[y].length - 1 && maze[y][x + 1] == ' ')
                    canRotateRight = true;
                break;
            case 1: // right
                if (x < maze[y].length - 1 && maze[y][x + 1] == ' ')
                    canMoveForward = true;
                if (y > 0 && maze[y - 1][x] == ' ')
                    canRotateLeft = true;
                if (y < maze.length - 1 && maze[y + 1][x] == ' ')
                    canRotateRight = true;
                break;
            case 2: // down
                if (y < maze.length - 1 && maze[y + 1][x] == ' ')
                    canMoveForward = true;
                if (x < maze[y].length - 1 && maze[y][x + 1] == ' ')
                    canRotateLeft = true;
                if (x > 0 && maze[y][x - 1] == ' ')
                    canRotateRight = true;
                break;
            case 3: // left
                if (x > 0 && maze[y][x - 1] == ' ')
                    canMoveForward = true;
                if (y < maze.length - 1 && maze[y + 1][x] == ' ')
                    canRotateLeft = true;
                if (y > 0 && maze[y - 1][x] == ' ')
                    canRotateRight = true;
                break;
            default:
                break;
        }

        ArrayList<Integer> options = new ArrayList<Integer>();
        if (canMoveForward)
            for (int i = 1; i < 3; i++)
                options.add(0);
        if (canRotateLeft)
            options.add(1);
        if (canRotateRight)
            options.add(2);
        if (options.size() == 0) {
            options.add(1);
            options.add(2);
        }

        int random = (int)(Math.random() * options.size());
        switch(options.get(random)) {
            case 0: // move forward
                switch(dir) {
                    case 0: // up
                        loc.changeY(-1);
                        break;
                    case 1: // right
                        loc.changeX(1);
                        break;
                    case 2: // down
                        loc.changeY(1);
                        break;
                    case 3: // left
                        loc.changeX(-1);
                        break;
                    default:
                        break;
                }
                break;
            case 1: // rotate left
                dir--;
                if (dir < 0)
                    dir = 3;
                break;
            case 2: // rotate right
                dir++;
                if (dir > 3)
                    dir = 0;
                break;
            default:
                break;
        }

    }

}