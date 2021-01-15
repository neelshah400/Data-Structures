import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JuliaSetProgram extends JPanel implements AdjustmentListener, MouseListener, ActionListener {

    JFrame frame;

    JPanel bigPanel, labelPanel, scrollPanel, buttonPanel;
    GridLayout grid;

    ControlBar a, b, oHue, oSaturation, oBrightness, iHue, iSaturation, iBrightness, zoom, radius, maxIterations, multiplier;
    ControlBar[] controlBars;

    JButton clear, save;

    BufferedImage image;
    String dir;
    JFileChooser fileChooser;

    int pixels;

    public JuliaSetProgram() {

        // initializing JFrame
        frame = new JFrame("Julia Set Program");
        frame.add(this);
        frame.setSize(1200, 800);
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
        radius = new ControlBar("Radius", 6000, 1000, 10000, 1000f);
        maxIterations = new ControlBar("Iterations (Max)", 300, 25, 1000, 1f);
        multiplier = new ControlBar("Multiplier", 2000, 1000, 10000, 1000f);
        controlBars = new ControlBar[]{
            a, b,
            oHue, oSaturation, oBrightness,
            iHue, iSaturation, iBrightness,
            zoom, radius, maxIterations, multiplier
        };
        for (ControlBar controlBar : controlBars) {
            controlBar.getBar().addAdjustmentListener(this);
            controlBar.getBar().addMouseListener(this);
        }

        // initializing 1 column GridLayout
        grid = new GridLayout(controlBars.length, 1);

        // adding labels to JPanel with 1 column GridLayout
        labelPanel = new JPanel();
        labelPanel.setLayout(grid);
        for (ControlBar controlBar : controlBars)
            labelPanel.add(controlBar.getLabel());

        // adding scrollbars to JPanel with 1 column GridLayout
        scrollPanel = new JPanel();
        scrollPanel.setLayout(grid);
        for (ControlBar controlBar : controlBars)
            scrollPanel.add(controlBar.getBar());

        // setting up clear and save buttons
        clear = new JButton("Clear");
        clear.addActionListener(this);
        save = new JButton("Save");
        save.addActionListener(this);

        // adding buttons to JPanel with 1 column GridLayout
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));
        buttonPanel.add(clear);
        buttonPanel.add(save);

        // adding panels to bigger JPanel with BorderLayout
        bigPanel = new JPanel();
        bigPanel.setLayout(new BorderLayout());
        bigPanel.add(labelPanel, BorderLayout.WEST);
        bigPanel.add(scrollPanel, BorderLayout.CENTER);
        bigPanel.add(buttonPanel, BorderLayout.EAST);

        // adding big JPanel to JFrame
        frame.add(bigPanel, BorderLayout.SOUTH);

        // making JFrame visible
        frame.setVisible(true);
        pixels = 1;

        // setting up code for saving images
        dir = System.getProperty("user.dir");
        fileChooser = new JFileChooser(dir);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); // giant eraser
        g.drawImage(drawJulia(), 0, 0, null);
    }

    public BufferedImage drawJulia() {

        // getting width and height of drawable window
        int w = frame.getWidth();
        int h = frame.getHeight();

        // initializing BufferedImage
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < w; x += pixels) { // looping through the width of the window
            for (int y = 0; y < h; y += pixels) { // looping through the height of the window

                // setting iterations, zx, and zy
                float iterations = maxIterations.getValue();
                double zx = 1.5 * ((x - (0.5 * w)) / (0.5 * zoom.getValue() * w));
                double zy = ((y - (0.5 * h)) / (0.5 * zoom.getValue() * h));

                // pre-test loop
                while ((zx * zx) + (zy * zy) < radius.getValue() && iterations > 0) {
                    double temp = (zx * zx) - (zy * zy) + a.getValue();
                    zy = (multiplier.getValue() * zx * zy) + b.getValue();
                    zx = temp;
                    iterations--;
                }

                // setting the color of the pixel at (x, y)
                int c;
                if (iterations > 0)
                    c = Color.HSBtoRGB(oHue.getValue() * (iterations / maxIterations.getValue()) % 1, oSaturation.getValue(), oBrightness.getValue());
                else
                    c = Color.HSBtoRGB(iHue.getValue(), iSaturation.getValue(), iBrightness.getValue());
                image.setRGB(x, y, c);

            }
        }

        return image;

    }

    public void saveImage() {
        if (image != null) {
            FileFilter filter = new FileNameExtensionFilter("*.png","png");
            fileChooser.setFileFilter(filter);
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    String str = file.getAbsolutePath();
                    if (str.indexOf(".png") >= 0)
                        str = str.substring(0, str.length() - 4);
                    ImageIO.write(image, "png", new File(str + ".png"));
                } catch(IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void adjustmentValueChanged(AdjustmentEvent e) {
        for (ControlBar controlBar : controlBars) {
            if (e.getSource() == controlBar.getBar())
                controlBar.update();
        }
        repaint();
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        pixels = 3;
    }

    public void mouseReleased(MouseEvent e) {
        pixels = 1;
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clear) {
            for (ControlBar controlBar : controlBars)
                controlBar.getBar().setValue(controlBar.getInitial());
        }
        if (e.getSource() == save)
            saveImage();
    }

    public static void main(String[] args) {
        JuliaSetProgram app = new JuliaSetProgram();
    }

}