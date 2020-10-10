import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class MazeProgram extends JPanel implements KeyListener {

    char[][] maze = new char[52][78];
    Hero hero;
    ArrayList<Wall> walls;
    JFrame frame;
    int size = 14;
    boolean use3D = false;

    public MazeProgram() {

        fillMaze();
        hero = new Hero(new Location(0, 1), 1, size, Color.RED);
        walls = getWalls(5, 50);

        frame = new JFrame("Maze");
        frame.add(this);
        frame.setSize(1200, 800);
        frame.addKeyListener(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g); // giant eraser
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, frame.getWidth(), frame.getHeight());

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
        }
        else {
            walls = getWalls(5, 50);
            for (Wall wall : walls) {
                g2.setColor(wall.getColor());
                if (wall.isFilled())
                    g2.fillPolygon(wall.getPoly());
                else
                    g2.drawPolygon(wall.getPoly());
            }
        }

    }

    public ArrayList<Wall> getWalls(int depth, int size) {

        ArrayList<Wall> list = new ArrayList<Wall>();
        int x = hero.getLoc().getX();
        int y = hero.getLoc().getY();
        int[] xCoordinates;
        int[] yCoordinates;
        Color color;
        
        int startX = 100;
        int endX = 800;
        int startY = 100;
        int endY = 700;

        for (int fov = 0; fov < depth; fov++) {

            // left rectangles
            xCoordinates = new int[] {startX + (size * fov), startX + (size * (fov + 1)), startX + (size * (fov + 1)), startX + (size * fov)};
            yCoordinates = new int[] {startY + (size * fov), startY + (size * fov), endY - (size * fov), endY - (size * fov)};
            color = Color.DARK_GRAY;
            list.add(new Wall(xCoordinates, yCoordinates, color, false));

            // right rectangles
            xCoordinates = new int[] {endX - (size * fov), endX - (size * (fov + 1)), endX - (size * (fov + 1)), endX - (size * fov)};
            yCoordinates = new int[] {startY + (size * (fov)), startY + (size * fov), endY - (size * fov), endY - (size * (fov))};
            color = Color.DARK_GRAY;
            list.add(new Wall(xCoordinates, yCoordinates, color, false));

            // left trapezoids
            if (    fov != 0 &&
                    (hero.getDir() == 0 && y - fov >= 0 && x - 1 >= 0 && maze[y - fov][x - 1] == '#') ||
                    (hero.getDir() == 1 && y - 1 >= 0 && x + fov <= maze[0].length - 1 && maze[y - 1][x + fov] == '#') ||
                    (hero.getDir() == 2 && y + fov <= maze.length - 1 && x + 1 <= maze[0].length - 1&& maze[y + fov][x + 1] == '#') ||
                    (hero.getDir() == 3 && y + 1 <= maze.length - 1 && x - fov >= 0 && maze[y + 1][x - fov] == '#')
            ) {
                xCoordinates = new int[] {startX + (size * fov), startX + (size * (fov + 1)), startX + (size * (fov + 1)), startX + (size * fov)};
                yCoordinates = new int[] {startY + (size * (fov - 1)), startY + (size * fov), endY - (size * fov), endY - (size * (fov - 1))};
                color = Color.GRAY;
                list.add(new Wall(xCoordinates, yCoordinates, color, true));
                color = Color.DARK_GRAY;
                list.add(new Wall(xCoordinates, yCoordinates, color, false));
            }

            // right trapezoids
            if (    fov != 0 &&
                    (hero.getDir() == 0 && y - fov >= 0 && x + 1 <= maze[0].length - 1 && maze[y - fov][x + 1] == '#') ||
                    (hero.getDir() == 1 && y + 1 <= maze.length - 1 && x + fov <= maze[0].length - 1 && maze[y + 1][x + fov] == '#') ||
                    (hero.getDir() == 2 && y + fov <= maze.length - 1 && x - 1 >= 0 && maze[y + fov][x - 1] == '#') ||
                    (hero.getDir() == 3 && y - 1 >= 0 && x - fov <= maze[0].length - 1 && maze[y - 1][x - fov] == '#')
            ) {
                xCoordinates = new int[] {endX - (size * fov), endX - (size * (fov + 1)), endX - (size * (fov + 1)), endX - (size * fov)};
                yCoordinates = new int[] {startY + (size * (fov - 1)), startY + (size * fov), endY - (size * fov), 700 - (size * (fov - 1))};
                color = Color.GRAY;
                list.add(new Wall(xCoordinates, yCoordinates, color, true));
                color = Color.DARK_GRAY;
                list.add(new Wall(xCoordinates, yCoordinates, color, false));
            }

            // top trapezoids
            xCoordinates = new int[] {startX + (size * fov), endX - (size * fov), endX - (size * (fov + 1)), startX + (size * (fov + 1))};
            yCoordinates = new int[] {startY + (size * (fov - 1)), startY + (size * (fov - 1)), startY + (size * fov), startY + (size * fov)};
            color = Color.GRAY;
            list.add(new Wall(xCoordinates, yCoordinates, color, true));
            color = Color.DARK_GRAY;
            list.add(new Wall(xCoordinates, yCoordinates, color, false));

            // bottom trapezoids
            xCoordinates = new int[] {startX + (size * fov), endX - (size * fov), endX - (size * (fov + 1)), startX + (size * (fov + 1))};
            yCoordinates = new int[] {endY - (size * (fov - 1)), endY - (size * (fov - 1)), endY - (size * fov), endY - (size * fov)};
            color = Color.GRAY;
            list.add(new Wall(xCoordinates, yCoordinates, color, true));
            color = Color.DARK_GRAY;
            list.add(new Wall(xCoordinates, yCoordinates, color, false));

            // square
            if (    (hero.getDir() == 0 && y - fov >= 0 && maze[y - fov][x] == '#') ||
                    (hero.getDir() == 1 && x + fov <= maze[0].length - 1 && maze[y][x + fov] == '#') ||
                    (hero.getDir() == 2 && y + fov <= maze.length - 1 && maze[y + fov][x] == '#') ||
                    (hero.getDir() == 3 && x - fov >= 0 && maze[y][x - fov] == '#')
            ) {
                System.out.println(fov);
                xCoordinates = new int[] {startX + (size * fov), endX - (size * fov), endX - (size * fov), startX + (size * fov)};
                yCoordinates = new int[] {startY + (size * (fov - 1)), startY + (size * (fov - 1)), endY - (size * (fov - 1)), endY - (size * (fov - 1))};
                color = Color.GRAY;
                list.add(new Wall(xCoordinates, yCoordinates, color, true));
                color = Color.DARK_GRAY;
                list.add(new Wall(xCoordinates, yCoordinates, color, false));
                break;
            }

        }

        return list;

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
        if (e.getKeyCode() == 32)
            use3D = !use3D;
        else
            hero.move(e.getKeyCode(), maze);
        repaint();
    }

    public void keyReleased(KeyEvent e) { }

    public void keyTyped(KeyEvent e) { }

    public static void main(String[] args) {
        MazeProgram app = new MazeProgram();
    }

}
