import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MazeProgram extends JPanel implements KeyListener {

    char[][] maze = new char[52][78];
    Hero hero;
    JFrame frame;
    int size = 15;

    public MazeProgram() {

        fillMaze();

        hero = new Hero(new Location(0, 1), 1, size, Color.RED);

        frame = new JFrame("Maze");
        frame.add(this);
        frame.setSize((maze[0].length + 8) * size, (maze.length + 8) * size);
        frame.addKeyListener(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g); // giant eraser
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, frame.getWidth(), frame.getHeight());

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
        hero.move(e.getKeyCode(), maze);
        repaint();
    }

    public void keyReleased(KeyEvent e) { }

    public void keyTyped(KeyEvent e) { }

    public static void main(String[] args) {
        MazeProgram app = new MazeProgram();
    }

}
