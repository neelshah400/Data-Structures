import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class JuliaSetProgram extends JPanel implements AdjustmentListener {

    JFrame frame;

    JLabel aLabel, bLabel;
    JScrollBar aBar, bBar;
    double a, b;

    float maxIterations;
    DecimalFormat decimalFormat;

    public JuliaSetProgram() {

        // initializing JFrame
        frame = new JFrame("Julia Set Program");
        frame.add(this);
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // initializing EmptyBorder for padding on the right
        EmptyBorder border = new EmptyBorder(0, 0, 0, 24);

        // setting up control bar for A
        aBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 6000);
        aBar.addAdjustmentListener(this);
        a = aBar.getValue() / 1000.0;
        aLabel = new JLabel("A: " + a);
        aLabel.setBorder(border);

        // setting up control bar for B
        bBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 6000);
        bBar.addAdjustmentListener(this);
        b = bBar.getValue() / 1000.0;
        bLabel = new JLabel("B: " + b);
        bLabel.setBorder(border);

        // initializing 1 column GridLayout
        GridLayout grid = new GridLayout(2, 1);

        // adding labels to JPanel with 1 column GridLayout
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(grid);
        labelPanel.add(aLabel);
        labelPanel.add(bLabel);

        // adding scrollbars to JPanel with 1 column GridLayout
        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(grid);
        scrollPanel.add(aBar);
        scrollPanel.add(bBar);

        // adding panels to bigger JPanel with BorderLayout
        JPanel bigPanel = new JPanel();
        bigPanel.setLayout(new BorderLayout());
        bigPanel.add(labelPanel, BorderLayout.WEST);
        bigPanel.add(scrollPanel, BorderLayout.CENTER);

        // adding big JPanel to JFrame
        frame.add(bigPanel, BorderLayout.SOUTH);

        // making JFrame visible
        frame.setVisible(true);

        // setting maximum iterations
        maxIterations = 300;

        // setting DecimalFormat to show 3 decimal places
        decimalFormat = new DecimalFormat("#.000");

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); // giant eraser
        g.drawImage(drawJulia(), 0, 0, null);
    }

    public BufferedImage drawJulia() {

        // get width and height of drawable window
        int w = frame.getWidth();
        int h = frame.getHeight();

        // initialize BufferedImage
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < w; x++) { // loop through thw width of the window
            for (int y = 0; y < h; y++) { // loop through the height of the window

                // setting zoom, iterations, zx, and zy
                int zoom = 1;
                float iterations = maxIterations;
                double zx = 1.5 * ((x - (0.5 * w)) / (0.5 * zoom * h));
                double zy = ((y - (0.5 * h)) / (0.5 * zoom * h));

                // pre-test loop
                while ((zx * zx) + (zy * zy) < 6.0 && iterations > 0) {
                    double temp = (zx * zx) - (zy * zy) + a;
                    zy = (2 * zx * zy) + b;
                    zx = temp;
                    iterations--;
                }

                // setting the color of the pixel at (x, y)
                int c;
                if (iterations > 0)
                    c = Color.HSBtoRGB((maxIterations / iterations) % 1, 1, 1);
                else
                    c = Color.HSBtoRGB(maxIterations / iterations, 1, 0);
                image.setRGB(x, y, c);

            }
        }

        return image;

    }

    public void adjustmentValueChanged(AdjustmentEvent e) {

        // adjusting the value of A
        if (e.getSource() == aBar) {
            a = aBar.getValue() / 1000.0;
            aLabel.setText("A: " + decimalFormat.format(a));
        }

        // adjusting the value of B
        if (e.getSource() == bBar) {
            b = bBar.getValue() / 1000.0;
            bLabel.setText("B: " + decimalFormat.format(b));
        }

        repaint();

    }

    public static void main(String[] args) {
        JuliaSetProgram app = new JuliaSetProgram();
    }

}