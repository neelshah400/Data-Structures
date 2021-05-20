import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class PaintProgram extends JPanel implements ActionListener, KeyListener, ChangeListener, AdjustmentListener, MouseMotionListener, MouseListener {

    JFrame frame;

    JMenu fileMenu;
    ImageIcon loadIcon, saveIcon;
    JMenuItem clearItem, loadItem, saveItem, exitItem;
    JMenuItem[] fileItems;

    Color[] colors;
    Color fgColor, bgColor, oldColor;
    JMenu colorMenu;
    JMenuItem[] colorItems;
    JColorChooser colorChooser;

    ImageIcon freeLineIcon, rectIcon, ovalIcon, eraserIcon, undoIcon, redoIcon;
    JButton freeLineButton, rectButton, ovalButton, eraserButton, undoButton, redoButton;

    JScrollBar penWidthScrollBar;
    int penWidth;

    JMenuBar menuBar;

    JFileChooser fileChooser;
    BufferedImage image;

    Stack<Object> shapes, history;
    ArrayList<Point> points;
    Shape shape;
    boolean drawingFreeLine, drawingRect, drawingOval, usingEraser;
    boolean firstClick;
    int x, y, width, height;

    public PaintProgram() {

        // panel
        this.addMouseMotionListener(this);
        this.addMouseListener(this);

        // file menu

        fileMenu = new JMenu("File");

        clearItem = new JMenuItem("New");

        loadIcon = new ImageIcon("loadImg.png");
        loadIcon = new ImageIcon(loadIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        loadItem = new JMenuItem("Load", KeyEvent.VK_L);
        loadItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        loadItem.setIcon(loadIcon);

        saveIcon = new ImageIcon("saveImg.png");
        saveIcon = new ImageIcon(saveIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        saveItem = new JMenuItem("Save", KeyEvent.VK_S);
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveItem.setIcon(saveIcon);

        exitItem = new JMenuItem("Exit");

        fileItems = new JMenuItem[]{clearItem, loadItem, saveItem, exitItem};

        for (JMenuItem item : fileItems) {
            item.addActionListener(this);
            fileMenu.add(item);
        }

        // color menu
        colors = new Color[]{Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA};
        fgColor = colors[0];
        bgColor = Color.BLACK;
        colorMenu = new JMenu("Color");
        colorMenu.setLayout(new GridLayout(colors.length, 1));
        colorItems = new JMenuItem[colors.length];
        for (int i = 0; i < colorItems.length; i++) {
            colorItems[i] = new JMenuItem();
            colorItems[i].setBackground(colors[i]);
            colorItems[i].setPreferredSize(new Dimension(50, 30));
            colorItems[i].setFocusable(false);
            colorItems[i].addActionListener(this);
            colorItems[i].putClientProperty("colorIndex", i);
            colorMenu.add(colorItems[i]);
        }
        colorChooser = new JColorChooser();
        colorChooser.getSelectionModel().addChangeListener(this);
        colorMenu.add(colorChooser);

        // drawing mode buttons
        
        freeLineIcon = new ImageIcon("freeLineImg.png");
        freeLineIcon = new ImageIcon(freeLineIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        freeLineButton = new JButton();
        freeLineButton.setIcon(freeLineIcon);
        freeLineButton.setFocusPainted(false);
        freeLineButton.setBackground(Color.LIGHT_GRAY);
        freeLineButton.setFocusable(false);
        freeLineButton.addActionListener(this);

        rectIcon = new ImageIcon("rectImg.png");
        rectIcon = new ImageIcon(rectIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        rectButton = new JButton();
        rectButton.setIcon(rectIcon);
        rectButton.setFocusPainted(false);
        rectButton.setFocusable(false);
        rectButton.addActionListener(this);

        ovalIcon = new ImageIcon("ovalImg.png");
        ovalIcon = new ImageIcon(ovalIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        ovalButton = new JButton();
        ovalButton.setIcon(ovalIcon);
        ovalButton.setFocusPainted(false);
        ovalButton.setFocusable(false);
        ovalButton.addActionListener(this);

        eraserIcon = new ImageIcon("eraserImg.png");
        eraserIcon = new ImageIcon(eraserIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        eraserButton = new JButton();
        eraserButton.setIcon(eraserIcon);
        eraserButton.setFocusPainted(false);
        eraserButton.setFocusable(false);
        eraserButton.addActionListener(this);

        undoIcon = new ImageIcon("undoImg.png");
        undoIcon = new ImageIcon(undoIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        undoButton = new JButton();
        undoButton.setIcon(undoIcon);
        undoButton.setFocusPainted(false);
        undoButton.setFocusable(false);
        undoButton.addActionListener(this);

        redoIcon = new ImageIcon("redoImg.png");
        redoIcon = new ImageIcon(redoIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        redoButton = new JButton();
        redoButton.setIcon(redoIcon);
        redoButton.setFocusPainted(false);
        redoButton.setFocusable(false);
        redoButton.addActionListener(this);

        // pen width scroll bar
        penWidthScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, 1, 0, 1, 40);
        penWidthScrollBar.setFocusable(false);
        penWidthScrollBar.addAdjustmentListener(this);
        penWidth = penWidthScrollBar.getValue();

        // menu bar
        menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(colorMenu);
        menuBar.add(freeLineButton);
        menuBar.add(rectButton);
        menuBar.add(ovalButton);
        menuBar.add(eraserButton);
        menuBar.add(undoButton);
        menuBar.add(redoButton);
        menuBar.add(penWidthScrollBar);

        // frame
        frame = new JFrame("Paint");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        frame.add(this);
        frame.add(menuBar, BorderLayout.NORTH);
        frame.setVisible(true);

        // config
        fileChooser = new JFileChooser(System.getProperty("user.dir"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("*.png", "png"));
        shapes = new Stack<>();
        history = new Stack<>();
        points = new ArrayList<>();
        drawingFreeLine = true;
        drawingRect = false;
        drawingOval = false;
        usingEraser = false;
        firstClick = true;

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(bgColor);
        g2.fillRect(0, 0, frame.getWidth(), frame.getHeight());
        if (image != null)
            g2.drawImage(image, 0, 0, null);
        if (shapes != null) {
            for (Object shape : shapes)
                drawShape(g2, shape);
        }
        if (drawingFreeLine && points != null)
            drawShape(g2, points);
    }

    public void drawShape(Graphics2D g2, Object shape) {
        if (shape instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) shape;
            g2.setColor(rectangle.getColor());
            g2.setStroke(new BasicStroke(rectangle.getPenWidth()));
            g2.draw(rectangle.getShape());
        }
        else if (shape instanceof Oval) {
            Oval oval = (Oval) shape;
            g2.setColor(oval.getColor());
            g2.setStroke(new BasicStroke(oval.getPenWidth()));
            g2.draw(oval.getShape());
        }
        else {
            ArrayList<?> points = (ArrayList<?>) shape;
            if (points == null || points.size() == 0)
                return;
            Point point = (Point) points.get(0);
            g2.setColor(point.getColor());
            g2.setStroke(new BasicStroke(point.getPenWidth()));
            for (int i = 0; i < points.size() - 1; i++) {
                Point p1 = (Point) points.get(i);
                Point p2 = (Point) points.get(i + 1);
                g2.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            }
        }
    }

    public BufferedImage getImage() {
        BufferedImage img = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();
        this.paint(g2);
        g2.dispose();
        return img;
    }

    public void load() {
        fileChooser.showOpenDialog(null);
        try {
            File file = fileChooser.getSelectedFile();
            if (file == null)
                return;
            if (file.getAbsolutePath().contains(".png")) {
                image = ImageIO.read(file);
                shapes = new Stack<>();
                repaint();
            }
            else
                JOptionPane.showMessageDialog(null, "Wrong file type. Please select a PNG file");
        } catch (IOException ignored) {}
    }

    public void save() {
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                String path = file.getAbsolutePath();
                if (!path.contains(".png"))
                    path += ".png";
                ImageIO.write(getImage(), "png", new File(path));
            } catch (IOException ignored) {}
        }
    }

    public void undo() {
        if (!shapes.isEmpty()) {
            history.push(shapes.pop());
            repaint();
        }
    }

    public void redo() {
        if (!history.isEmpty()) {
            shapes.push(history.pop());
            repaint();
        }
    }

    public void changeDrawingMode(boolean drawingFreeLine, boolean drawingRect, boolean drawingOval, boolean usingEraser) {
        this.drawingFreeLine = drawingFreeLine;
        this.drawingRect = drawingRect;
        this.drawingOval = drawingOval;
        this.usingEraser = usingEraser;
        freeLineButton.setBackground(drawingFreeLine && !usingEraser ? Color.LIGHT_GRAY : null);
        rectButton.setBackground(drawingRect ? Color.LIGHT_GRAY : null);
        ovalButton.setBackground(drawingOval ? Color.LIGHT_GRAY : null);
        eraserButton.setBackground(usingEraser ? Color.LIGHT_GRAY : null);
        if (usingEraser) {
            oldColor = fgColor;
            fgColor = bgColor;
        }
        else
            fgColor = oldColor;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clearItem) {
            image = null;
            shapes = new Stack<>();
            repaint();
        }
        else if (e.getSource() == loadItem)
            load();
        else if (e.getSource() == saveItem)
            save();
        else if (e.getSource() == exitItem)
            System.exit(0);
        else if (e.getSource() == freeLineButton)
            changeDrawingMode(true, false, false, false);
        else if (e.getSource() == rectButton)
            changeDrawingMode(false, true, false, false);
        else if (e.getSource() == ovalButton)
            changeDrawingMode(false, false, true, false);
        else if (e.getSource() == eraserButton)
            changeDrawingMode(true, false, false, true);
        else if (e.getSource() == undoButton)
            undo();
        else if (e.getSource() == redoButton)
            redo();
        else {
            if (usingEraser)
                changeDrawingMode(true, false, false, false);
            int index = (int) ((JMenuItem) e.getSource()).getClientProperty("colorIndex");
            fgColor = colors[index];
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.isControlDown()) {
            if (e.getKeyCode() == KeyEvent.VK_Z)
                undo();
            if (e.getKeyCode() == KeyEvent.VK_Y)
                redo();
        }
    }

    public void keyTyped(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {}

    public void stateChanged(ChangeEvent e) {
        fgColor = colorChooser.getColor();
    }

    public void adjustmentValueChanged(AdjustmentEvent e) {
        penWidth = penWidthScrollBar.getValue();
    }

    public void mouseDragged(MouseEvent e) {
        if (drawingRect || drawingOval) {
            if (firstClick) {
                x = e.getX();
                y = e.getY();
                if (drawingRect)
                    shape = new Rectangle(x, y, 0, 0, fgColor, penWidth);
                else if (drawingOval)
                    shape = new Oval(x, y, 0, 0, fgColor, penWidth);
                shapes.push(shape);
                firstClick = false;
            }
            else {
                width = Math.abs(e.getX() - x);
                height = Math.abs(e.getY() - y);
                shape.setWidth(width);
                shape.setHeight(height);
                if (x <= e.getX() && y >= e.getY())
                    shape.setY(e.getY());
                else if (x >= e.getX() && y <= e.getY())
                    shape.setX(e.getX());
                else if (x >= e.getX() && y >= e.getY()) {
                    shape.setX(e.getX());
                    shape.setY(e.getY());
                }
            }
        }
        else
            points.add(new Point(e.getX(), e.getY(), fgColor, penWidth));
        repaint();
    }

    public void mouseReleased(MouseEvent e) {
        if (drawingRect || drawingOval)
            firstClick = true;
        else {
            shapes.push(points);
            points = new ArrayList<>();
        }
        history = new Stack<>();
        repaint();
    }

    public void mouseMoved(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public static void main(String[] args) {
        PaintProgram app = new PaintProgram();
    }

    public class Point {

        private int x, y;
        private Color color;
        private int penWidth;

        public Point(int x, int y, Color color, int penWidth) {
            this.x = x;
            this.y = y;
            this.color = color;
            this.penWidth = penWidth;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public Color getColor() {
            return color;
        }

        public int getPenWidth() {
            return penWidth;
        }

    }

    public class Shape extends Point {

        private int width, height;

        public Shape(int x, int y, int width, int height, Color color, int penWidth) {
            super(x, y, color, penWidth);
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

    }

    public class Rectangle extends Shape {

        public Rectangle(int x, int y, int width, int height, Color color, int penWidth) {
            super(x, y, width, height, color, penWidth);
        }

        public Rectangle2D.Double getShape() {
            return new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
        }

    }

    public class Oval extends Shape {

        public Oval(int x, int y, int width, int height, Color color, int penWidth) {
            super(x, y, width, height, color, penWidth);
        }

        public Ellipse2D.Double getShape() {
            return new Ellipse2D.Double(getX(), getY(), getWidth(), getHeight());
        }

    }

}