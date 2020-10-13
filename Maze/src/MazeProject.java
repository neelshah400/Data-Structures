import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MazeProject extends JPanel implements KeyListener {

    JFrame frame;

    int x = 600;
    int y = 350;

    public MazeProject() {
        frame = new JFrame("A-Mazing Program");
        frame.add(this);
        frame.setSize(1200, 700);
        frame.addKeyListener(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g); // giant eraser
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, frame.getWidth(), frame.getHeight());

        g2.setColor(Color.RED);
        g2.fillOval(x, y, 30, 30);

        g2.setColor(Color.CYAN);
        g2.setStroke(new BasicStroke(5));
        g2.drawOval(x, y, 30, 30);

    }

    public void keyPressed(KeyEvent e) {

        System.out.println(e.getKeyCode());

        if (e.getKeyCode() == 37) // left
            x -= 5;
        if (e.getKeyCode() == 38) // up
            y -= 5;
        if (e.getKeyCode() == 39) // right
            x += 5;
        if (e.getKeyCode() == 40) // down
            y += 5;

        repaint();

    }

    public void keyReleased(KeyEvent e) { }

    public void keyTyped(KeyEvent e) { }

    public static void main(String[] args) {
        MazeProject app = new MazeProject();
    }

}
