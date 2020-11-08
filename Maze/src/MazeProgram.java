import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MazeProgram extends JPanel implements KeyListener {

    char[][] maze = new char[52][78];
    Hero hero;
    ArrayList<Monster> monsters;
    ArrayList<Wall> walls;
    String[] directions = {"N", "E", "S", "W"};

    JFrame frame;
    Font font;

    int size = 14;
    boolean use3D = false;
    boolean won = false;

    Location start = new Location(0, 1);
//    Location start = new Location(5, 45); // start near end (for testing purposes)
    Location end = new Location(0, 45);

    ScheduledExecutorService service;

    public MazeProgram() {

        fillMaze();
        hero = new Hero(new Location(start.getX(), start.getY()), 1, size, Color.GREEN);
        monsters = getMonsters(10);
        walls = getWalls(5, 50);

        frame = new JFrame("Maze");
        frame.add(this);
        frame.setSize(1400, 800);
        frame.addKeyListener(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        font = new Font("Courier", Font.PLAIN, 36);

        service = Executors.newSingleThreadScheduledExecutor();
        for (Monster monster : monsters) {
            service.scheduleAtFixedRate(() -> {
                monster.move(maze);
                repaint();
            }, 1000, monster.getSpeed(), TimeUnit.MILLISECONDS);
        }

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g); // giant eraser
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(font);

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, frame.getWidth(), frame.getHeight());

        g2.setColor(Color.PINK);
        g2.drawString("Moves: " + hero.getMoves(), 1150, 50);
        g2.drawString("Direction: " + directions[hero.getDir()], 1150, 100);

        int heroX = hero.getLoc().getX();
        int heroY = hero.getLoc().getY();
        for (Monster monster : monsters) {
            int monsterX = monster.getLoc().getX();
            int monsterY = monster.getLoc().getY();
            if (heroX == monsterX && heroY == monsterY) {
                g2.setColor(Color.RED);
                g2.drawString("You lose!", 1150, 150);
                service.shutdown();
            }
        }
        if (!service.isShutdown() && hero.getMoves() != 0 && heroX == end.getX() && heroY == end.getY())
            won = true;

        if (won) {
            g2.setColor(Color.GREEN);
            g2.drawString("You win!", 1150, 150);
            service.shutdown();
        }

        if (!use3D) {
            g2.setColor(Color.GRAY);
            for (int y = 0; y < maze.length; y++) {
                for (int x = 0; x < maze[y].length; x++) {
                    if (maze[y][x] == ' ')
                        g2.fillRect(x * size + size, y * size + size, size, size);
                    else
                        g2.drawRect(x * size + size, y * size + size, size, size);
                }
            }
            g2.setColor(hero.getColor());
            g2.fill(hero.getRect());
            for (Monster monster : monsters) {
                g2.setColor(monster.getColor());
                g2.fill(monster.getRect());
            }
        }
        else {

            int offsetX = 1150;
            int offsetY = 200;

            g2.setColor(Color.GRAY);
            for (int y = heroY - 2; y <= heroY + 2; y++) {
                for (int x = heroX - 2; x <= heroX + 2; x++) {
                    int drawX = x - (heroX - 2);
                    int drawY = y - (heroY - 2);
                    try {
                        if (maze[y][x] == ' ')
                            g2.fillRect(drawX * size + size + offsetX, drawY * size + size + offsetY, size, size);
                        else
                            g2.drawRect(drawX * size + size + offsetX, drawY * size + size + offsetY, size, size);
                    } catch (IndexOutOfBoundsException ignored) {

                    }
                }
            }

            g2.setColor(hero.getColor());
            g2.fill(hero.getRect(2, 2, offsetX, offsetY));

            for (Monster monster : monsters) {
                int monsterX = monster.getLoc().getX();
                int monsterY = monster.getLoc().getY();
                int relX = monsterX - (heroX - 2);
                int relY = monsterY - (heroY - 2);
                if (relX >= 0 && relX <= 4 && relY >= 0 && relY <= 4) {
                    g2.setColor(monster.getColor());
                    g2.fill(monster.getRect(relX, relY, offsetX, offsetY));
                }
            }

            walls = getWalls(5, 50);
            for (Wall wall : walls) {
                if (wall.getSpecialColor() != null)
                    g2.setColor(wall.getSpecialColor());
                else
                    g2.setPaint(wall.getPaint());
                if (wall.isFilled())
                    g2.fillPolygon(wall.getPoly());
                else
                    g2.drawPolygon(wall.getPoly());
            }

        }

    }

    public ArrayList<Monster> getMonsters(int n) {
        ArrayList<Monster> monsters = new ArrayList<Monster>();
        ArrayList<Location> locations = new ArrayList<Location>();
        locations.add(hero.getLoc());
        for (int i = 0; i < n; i++) {
            int y = -1;
            int x = -1;
            Location loc;
            int dir = -1;
            do {
                y = (int)(Math.random() * maze.length);
                x = (int)(Math.random() * maze[0].length);
                loc = new Location(x, y);
                dir = (int)(Math.random() * 4);
            } while (maze[y][x] != ' ' && !locations.contains(loc));
            monsters.add(new Monster(loc, dir, size, Color.RED));
            locations.add(loc);
        }
        return monsters;
    }

    public ArrayList<Wall> getWalls(int depth, int size) {

        ArrayList<Wall> list = new ArrayList<Wall>();
        int x = hero.getLoc().getX();
        int y = hero.getLoc().getY();
        int[] xCoordinates;
        int[] yCoordinates;

        int startX = 100;
        int endX = 800;
        int startY = 100;
        int endY = 700;

        for (int fov = 0; fov < depth; fov++) {

            // left rectangles
            xCoordinates = new int[] {startX + (size * fov), startX + (size * (fov + 1)), startX + (size * (fov + 1)), startX + (size * fov)};
            yCoordinates = new int[] {startY + (size * fov), startY + (size * fov), endY - (size * fov), endY - (size * fov)};
            list.add(new Wall("left rectangle", xCoordinates, yCoordinates, size, fov, false));

            // right rectangles
            xCoordinates = new int[] {endX - (size * fov), endX - (size * (fov + 1)), endX - (size * (fov + 1)), endX - (size * fov)};
            yCoordinates = new int[] {startY + (size * (fov)), startY + (size * fov), endY - (size * fov), endY - (size * (fov))};
            list.add(new Wall("right rectangle", xCoordinates, yCoordinates, size, fov, false));

            // left trapezoids
            if (!(  (hero.getDir() == 0 && y - fov >= 0 && x - 1 >= 0 && maze[y - fov][x - 1] == ' ') ||
                    (hero.getDir() == 1 && y - 1 >= 0 && x + fov <= maze[0].length - 1 && maze[y - 1][x + fov] == ' ') ||
                    (hero.getDir() == 2 && y + fov <= maze.length - 1 && x + 1 <= maze[0].length - 1&& maze[y + fov][x + 1] == ' ') ||
                    (hero.getDir() == 3 && y + 1 <= maze.length - 1 && x - fov >= 0 && maze[y + 1][x - fov] == ' ')
            )) {
                xCoordinates = new int[] {startX + (size * fov), startX + (size * (fov + 1)), startX + (size * (fov + 1)), startX + (size * fov)};
                yCoordinates = new int[] {startY + (size * (fov - 1)), startY + (size * fov), endY - (size * fov), endY - (size * (fov - 1))};
                list.add(new Wall("left trapezoid", xCoordinates, yCoordinates, size, fov, true));
                list.add(new Wall("left trapezoid", xCoordinates, yCoordinates, size, fov, false));
            }

            // right trapezoids
            if (!(  (hero.getDir() == 0 && y - fov >= 0 && x + 1 <= maze[0].length - 1 && maze[y - fov][x + 1] == ' ') ||
                    (hero.getDir() == 1 && y + 1 <= maze.length - 1 && x + fov <= maze[0].length - 1 && maze[y + 1][x + fov] == ' ') ||
                    (hero.getDir() == 2 && y + fov <= maze.length - 1 && x - 1 >= 0 && maze[y + fov][x - 1] == ' ') ||
                    (hero.getDir() == 3 && y - 1 >= 0 && x - fov >= 0 && maze[y - 1][x - fov] == ' ')
            )) {
                xCoordinates = new int[] {endX - (size * fov), endX - (size * (fov + 1)), endX - (size * (fov + 1)), endX - (size * fov)};
                yCoordinates = new int[] {startY + (size * (fov - 1)), startY + (size * fov), endY - (size * fov), 700 - (size * (fov - 1))};
                list.add(new Wall("right trapezoid", xCoordinates, yCoordinates, size, fov, true));
                list.add(new Wall("right trapezoid", xCoordinates, yCoordinates, size, fov, false));
            }

            // top trapezoids
            xCoordinates = new int[] {startX + (size * fov), endX - (size * fov), endX - (size * (fov + 1)), startX + (size * (fov + 1))};
            yCoordinates = new int[] {startY + (size * (fov - 1)), startY + (size * (fov - 1)), startY + (size * fov), startY + (size * fov)};
            list.add(new Wall("top trapezoid", xCoordinates, yCoordinates, size, fov, true));
            list.add(new Wall("top trapezoid", xCoordinates, yCoordinates, size, fov, false));

            // bottom trapezoids
            xCoordinates = new int[] {startX + (size * fov), endX - (size * fov), endX - (size * (fov + 1)), startX + (size * (fov + 1))};
            yCoordinates = new int[] {endY - (size * (fov - 1)), endY - (size * (fov - 1)), endY - (size * fov), endY - (size * fov)};
            list.add(new Wall("bottom trapezoid", xCoordinates, yCoordinates, size, fov, true));
            list.add(new Wall("bottom trapezoid", xCoordinates, yCoordinates, size, fov, false));

            // square
            if (!(  (hero.getDir() == 0 && y - fov >= 0 && maze[y - fov][x] == ' ') ||
                    (hero.getDir() == 1 && x + fov <= maze[0].length - 1 && maze[y][x + fov] == ' ') ||
                    (hero.getDir() == 2 && y + fov <= maze.length - 1 && maze[y + fov][x] == ' ') ||
                    (hero.getDir() == 3 && x - fov >= 0 && maze[y][x - fov] == ' ')
            )) {
                xCoordinates = new int[] {startX + (size * fov), endX - (size * fov), endX - (size * fov), startX + (size * fov)};
                yCoordinates = new int[] {startY + (size * (fov - 1)), startY + (size * (fov - 1)), endY - (size * (fov - 1)), endY - (size * (fov - 1))};
                if (getSpecialWallCondition(hero.getDir(), x, y, fov, start))
                    list.add(new Wall("square", xCoordinates, yCoordinates, size, fov, true, Color.BLACK)); // make start of maze black
                else if (getSpecialWallCondition(hero.getDir(), x, y, fov, end))
                    list.add(new Wall("square", xCoordinates, yCoordinates, size, fov, true, Color.BLACK)); // make end of maze black
                else
                    list.add(new Wall("square", xCoordinates, yCoordinates, size, fov, true));
                list.add(new Wall("square", xCoordinates, yCoordinates, size, fov, false));
                break;
            }

        }

        return list;

    }

    public boolean getSpecialWallCondition(int dir, int x, int y, int fov, Location loc) {
        return ((dir == 0 && y - fov == loc.getY() && x == loc.getX() - 1) ||
                (dir == 1 && y == loc.getY() && x + fov == loc.getX() - 1) ||
                (dir == 2 && y + fov == loc.getY() && x == loc.getX() - 1) ||
                (dir == 3 && y == loc.getY() && x - fov == loc.getX() - 1)
        );
    }

    public void fillMaze() {
        File file = new File("MazeFile.txt");
        try {
            BufferedReader input = new BufferedReader(new FileReader(file));
            String text;
            int y = 0;
            while ((text = input.readLine()) != null) {
                for (int x = 0; x < text.length(); x++)
                    maze[y][x] = text.charAt(x);
                y++;
            }
        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 32) // space bar
            use3D = !use3D;
        else if (!service.isShutdown())
            hero.move(e.getKeyCode(), maze);
        repaint();
    }

    public void keyReleased(KeyEvent e) { }

    public void keyTyped(KeyEvent e) { }

    public static void main(String[] args) {
        MazeProgram app = new MazeProgram();
    }

}
