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
//        hero = new Hero(new Location(0, 1), 1, size, Color.RED);
        hero = new Hero(new Location(5, 45), 3, size, Color.RED); // start near end (for testing purposes)
        walls = getWalls(5, 50);

        frame = new JFrame("Maze");
        frame.add(this);
        frame.setSize(1400, 800);
        frame.addKeyListener(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g); // giant eraser
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(new Font("Courier", Font.PLAIN, 36));

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, frame.getWidth(), frame.getHeight());

        g2.setColor(Color.PINK);
        g2.drawString("Moves: " + hero.getMoves(), 1150, 50);

        int heroX = hero.getLoc().getX();
        int heroY = hero.getLoc().getY();
        if (hero.getMoves() != 0 && (heroX == 0 || heroX == maze[0].length - 1 || heroY == 0 || heroY == maze.length - 1)) {
            g2.setColor(Color.GREEN);
            g2.drawString("You win!", 1150, 150);
        }

//        g2.setColor(Color.BLUE);
//        switch (hero.getDir()) {
//
//        }
//        g2.fillPolygon(new int[] {}, new int[] {}, 3);

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
                g2.setPaint(wall.getPaint());
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

        int startX = 100;
        int endX = 800;
        int startY = 100;
        int endY = 700;

        for (int fov = 0; fov < depth; fov++) {

            System.out.printf("Dir: %s, FOV: %s, X: %s, Y: %s\n", hero.getDir(), fov, x, y);

            // left rectangles
            System.out.println("\tleft rectangle");
            xCoordinates = new int[] {startX + (size * fov), startX + (size * (fov + 1)), startX + (size * (fov + 1)), startX + (size * fov)};
            yCoordinates = new int[] {startY + (size * fov), startY + (size * fov), endY - (size * fov), endY - (size * fov)};
            list.add(new Wall("left rectangle", xCoordinates, yCoordinates, size, fov, false));

            // right rectangles
            System.out.println("\tright rectangle");
            xCoordinates = new int[] {endX - (size * fov), endX - (size * (fov + 1)), endX - (size * (fov + 1)), endX - (size * fov)};
            yCoordinates = new int[] {startY + (size * (fov)), startY + (size * fov), endY - (size * fov), endY - (size * (fov))};
            list.add(new Wall("right rectangle", xCoordinates, yCoordinates, size, fov, false));

            // left trapezoids
            if (!(  (hero.getDir() == 0 && y - fov >= 0 && x - 1 >= 0 && maze[y - fov][x - 1] == ' ') ||
                    (hero.getDir() == 1 && y - 1 >= 0 && x + fov <= maze[0].length - 1 && maze[y - 1][x + fov] == ' ') ||
                    (hero.getDir() == 2 && y + fov <= maze.length - 1 && x + 1 <= maze[0].length - 1&& maze[y + fov][x + 1] == ' ') ||
                    (hero.getDir() == 3 && y + 1 <= maze.length - 1 && x - fov >= 0 && maze[y + 1][x - fov] == ' ')
            )) {
                System.out.println("\tleft trapezoid");
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
                System.out.println("\tright trapezoid");
                xCoordinates = new int[] {endX - (size * fov), endX - (size * (fov + 1)), endX - (size * (fov + 1)), endX - (size * fov)};
                yCoordinates = new int[] {startY + (size * (fov - 1)), startY + (size * fov), endY - (size * fov), 700 - (size * (fov - 1))};
                list.add(new Wall("right trapezoid", xCoordinates, yCoordinates, size, fov, true));
                list.add(new Wall("right trapezoid", xCoordinates, yCoordinates, size, fov, false));
            }

            // top trapezoids
            System.out.println("\ttop trapezoid");
            xCoordinates = new int[] {startX + (size * fov), endX - (size * fov), endX - (size * (fov + 1)), startX + (size * (fov + 1))};
            yCoordinates = new int[] {startY + (size * (fov - 1)), startY + (size * (fov - 1)), startY + (size * fov), startY + (size * fov)};
            list.add(new Wall("top trapezoid", xCoordinates, yCoordinates, size, fov, true));
            list.add(new Wall("top trapezoid", xCoordinates, yCoordinates, size, fov, false));

            // bottom trapezoids
            System.out.println("\tbottom trapezoid");
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
                System.out.println("\tsquare");
                xCoordinates = new int[] {startX + (size * fov), endX - (size * fov), endX - (size * fov), startX + (size * fov)};
                yCoordinates = new int[] {startY + (size * (fov - 1)), startY + (size * (fov - 1)), endY - (size * (fov - 1)), endY - (size * (fov - 1))};
                list.add(new Wall("square", xCoordinates, yCoordinates, size, fov, true));
                list.add(new Wall("square", xCoordinates, yCoordinates, size, fov, false));
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
