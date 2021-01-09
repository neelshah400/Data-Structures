import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;

public class JuliaSetProgram extends JPanel implements AdjustmentListener {

    JFrame frame;

    ControlBar a, b, oHue, oSaturation, oBrightness, iHue, iSaturation, iBrightness, zoom, maxIterations;
    ControlBar[] controlBars;

    public JuliaSetProgram() {

        // initializing JFrame
        frame = new JFrame("Julia Set Program");
        frame.add(this);
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // setting up control bars
        a = new ControlBar("A", 0, -2000, 2000, 1000f);
        b = new ControlBar("B", 0, -2000, 2000, 1000f);
        oHue = new ControlBar("Hue (Outside)", 0, 0, 1000, 1000f);
        oSaturation = new ControlBar("Saturation (Outside)", 1000, 0, 1000, 1000f);
        oBrightness = new ControlBar("Brightness (Outside)", 1000, 0, 1000, 1000f);
        iHue = new ControlBar("Hue (Inside)", 500, 0, 1000, 1000f);
        iSaturation = new ControlBar("Saturation (Inside)", 1000, 0, 1000, 1000f);
        iBrightness = new ControlBar("Brightness (Inside)", 1000, 0, 1000, 1000f);
        zoom = new ControlBar("Zoom", 1000, 250, 10000, 1000f);
        maxIterations = new ControlBar("Iterations (Max)", 300, 25, 1000, 1f);
        controlBars = new ControlBar[]{
            a, b,
            oHue, oSaturation, oBrightness,
            iHue, iSaturation, iBrightness,
            zoom, maxIterations
        };
        for (ControlBar controlBar : controlBars)
            controlBar.getBar().addAdjustmentListener(this);

        // initializing 1 column GridLayout
        GridLayout grid = new GridLayout(controlBars.length, 1);

        // adding labels to JPanel with 1 column GridLayout
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(grid);
        for (ControlBar controlBar : controlBars)
            labelPanel.add(controlBar.getLabel());

        // adding scrollbars to JPanel with 1 column GridLayout
        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(grid);
        for (ControlBar controlBar : controlBars)
            scrollPanel.add(controlBar.getBar());

        // adding panels to bigger JPanel with BorderLayout
        JPanel bigPanel = new JPanel();
        bigPanel.setLayout(new BorderLayout());
        bigPanel.add(labelPanel, BorderLayout.WEST);
        bigPanel.add(scrollPanel, BorderLayout.CENTER);

        // adding big JPanel to JFrame
        frame.add(bigPanel, BorderLayout.SOUTH);

        // making JFrame visible
        frame.setVisible(true);

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

                // setting iterations, zx, and zy
                float iterations = maxIterations.getValue();
                double zx = 1.5 * ((x - (0.5 * w)) / (0.5 * zoom.getValue() * w));
                double zy = ((y - (0.5 * h)) / (0.5 * zoom.getValue() * h));

                // pre-test loop
                while ((zx * zx) + (zy * zy) < 6.0 && iterations > 0) {
                    double temp = (zx * zx) - (zy * zy) + a.getValue();
                    zy = (2 * zx * zy) + b.getValue();
                    zx = temp;
                    iterations--;
                }

                // setting the color of the pixel at (x, y)
                int c;
                if (iterations > 0)
                    c = Color.HSBtoRGB(oHue.getValue() * (maxIterations.getValue() / iterations) % 1, oSaturation.getValue(), oBrightness.getValue());
                else
                    c = Color.HSBtoRGB(iHue.getValue(), iSaturation.getValue(), iBrightness.getValue());
                image.setRGB(x, y, c);

            }
        }

        return image;

    }

    public void adjustmentValueChanged(AdjustmentEvent e) {
        for (ControlBar controlBar : controlBars) {
            if (e.getSource() == controlBar.getBar())
                controlBar.update();
        }
        repaint();
    }

    public static void main(String[] args) {
        JuliaSetProgram app = new JuliaSetProgram();
    }

}