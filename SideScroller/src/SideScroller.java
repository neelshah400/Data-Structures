import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SideScroller extends JPanel implements Runnable, KeyListener {

    JFrame frame;

    HashMap<Integer, ArrayList<Block>> map;

    int count, smallCityCount, bigCityCount, cloudsCount;
    BufferedImage smallCityImage, bigCityImage, cloudsImage, sunsetImage;

    BufferedImage aladdinImage;
    BufferedImage[] runningImages;
    BufferedImage[] jumpingImages;
    BufferedImage blockImage;

    Hero hero;

    boolean gameOn;
    Thread timer;

    public SideScroller() {

        frame = new JFrame("Aladdin's Not-So-Exciting Adventure");
        frame.add(this);

        map = new HashMap<Integer, ArrayList<Block>>();

        count = 0;
        smallCityCount = 0;
        bigCityCount = 0;
        cloudsCount = 0;
        try {

            smallCityImage = ImageIO.read(new File("smallCity.png"));
            bigCityImage = ImageIO.read(new File("bigCity.png"));
            cloudsImage = ImageIO.read(new File("clouds.png"));
            sunsetImage = ImageIO.read(new File("sunset.png"));
            aladdinImage = ImageIO.read(new File("Aladdin.png"));
            blockImage = ImageIO.read(new File("box.png"));

            File file = new File("map.txt");
            BufferedReader input = new BufferedReader(new FileReader(file));
            String text;
            int row = 0;
            while ((text = input.readLine()) != null) {
                String[] pieces = text.split("");
                for (int x = 0; x < pieces.length; x++) {
                    if (pieces[x].equals("B")) {
                        if (!map.containsKey(x))
                            map.put(x, new ArrayList<Block>());
                        map.get(x).add(new Block(50 * x, 50 * row + 10, 50, 50));
                    }
                }
                row++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        int[][] runningCoordinates = new int[][]{
                // {x, y, w, h}
                {337, 3, 23, 55}, // standing
                {4, 64, 31, 53}, // running
                {34, 64, 31, 53},
                {62, 64, 31, 51},
                {92, 64, 31, 51},
                {127, 64, 37, 51},
                {166, 64, 31, 51},
                {205, 64, 31, 51},
                {233, 64, 30, 51},
                {263, 61, 30, 56},
                {292, 61, 34, 56},
                {325, 60, 41, 56},
                {367, 60, 36, 56}
        };
        runningImages = new BufferedImage[runningCoordinates.length];
        for (int i = 0; i < runningImages.length; i++) {
            runningImages[i] = aladdinImage.getSubimage(runningCoordinates[i][0], runningCoordinates[i][1], runningCoordinates[i][2], runningCoordinates[i][3]);
            runningImages[i] = resize(runningImages[i], 2);
        }

        int[][] jumpingCoordinates = new int[][]{
                // {x, y, w, h}
                {4, 294, 31, 59}, // jumping
                {35, 300, 29, 58},
                {62, 301, 38, 56},
                {100, 301, 36, 56},
                {140, 303, 41, 50},
                {183, 304, 49, 47},
                {230, 303, 42, 50}, // falling
                {278, 302, 37, 54},
                {321, 303, 33, 56},
                {4, 363, 35, 64},
                {42, 365, 36, 63},
                {168, 361, 25, 53}
        };
        jumpingImages = new BufferedImage[jumpingCoordinates.length];
        for (int i = 0; i < jumpingImages.length; i++) {
            jumpingImages[i] = aladdinImage.getSubimage(jumpingCoordinates[i][0], jumpingCoordinates[i][1], jumpingCoordinates[i][2], jumpingCoordinates[i][3]);
            jumpingImages[i] = resize(jumpingImages[i], 2);
        }

        hero = new Hero(200, 450, runningCoordinates, jumpingCoordinates);

        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        frame.setVisible(true);

        gameOn = true;
        timer = new Thread(this);
        timer.start();

    }

    public BufferedImage resize(BufferedImage image, int scale) {
        int width = image.getWidth() * scale;
        int height = image.getHeight() * scale;
        Image temp = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(temp, 0, 0, null);
        g2.dispose();
        return scaledImage;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(sunsetImage, 0, 0, this);
        g.drawImage(cloudsImage, cloudsCount - 960, 0, this);
        g.drawImage(cloudsImage, cloudsCount + 960, 0, this);
        g.drawImage(bigCityImage, bigCityCount - 960, 0, this);
        g.drawImage(bigCityImage, bigCityCount + 960, 0, this);
        g.drawImage(smallCityImage, smallCityCount - 960, 0, this);
        g.drawImage(smallCityImage, smallCityCount + 960, 0, this);
        int start = hero.getColumn() - hero.getOriginalColumn();
        int end = start + (900 / 50);
        for (int col = start; col <= end; col++) {
            try {
                for (Block block : map.get(col))
                    g.drawImage(blockImage, block.getX(), block.getY(), this);
            } catch (NullPointerException e) {

            }
        }
        System.out.println(hero.getColumn());
        if (hero.isJumping() || hero.isFalling())
            g.drawImage(jumpingImages[hero.getJumpingCount()], hero.getX(), hero.getY(), this);
        else
            g.drawImage(runningImages[hero.getRunningCount()], hero.getX(), hero.getY(), this);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLUE);
        for (String str : new String[] {"top", "bottom", "right", "left"})
            g2.draw(hero.getEdge(str));
    }

    public void run() {

        while (true) {

            if (gameOn) {

                boolean topCollision = false, bottomCollision = false, rightCollision = false, leftCollision = false;
                for (int col = hero.getColumn(); col <= hero.getColumn() + 1; col++) {
                    try {
                        for (Block block : map.get(col)) {
                            if (hero.getEdge("top").intersects(block.getCollisionBox()))
                                topCollision = true;
                            if (hero.getEdge("bottom").intersects(block.getCollisionBox()))
                                bottomCollision = true;
                            if (hero.getEdge("right").intersects(block.getCollisionBox()))
                                rightCollision = true;
                            if (hero.getEdge("left").intersects(block.getCollisionBox()))
                                leftCollision = true;
                        }
                    } catch (NullPointerException e) {

                    }
                }
//                for (ArrayList<Block> list : map.values()) {
//                    for (Block block : list) {
//                        if (hero.getEdge("top").intersects(block.getCollisionBox()))
//                            topCollision = true;
//                        if (hero.getEdge("bottom").intersects(block.getCollisionBox()))
//                            bottomCollision = true;
//                        if (hero.getEdge("right").intersects(block.getCollisionBox()))
//                            rightCollision = true;
//                        if (hero.getEdge("left").intersects(block.getCollisionBox()))
//                            leftCollision = true;
//                    }
//                }

                if (topCollision) {
                    hero.setJumping(false);
                    hero.setFalling(true);
                    hero.setJumpingCount(6);
                }
                if (bottomCollision)
                    hero.setFalling(false);
                else if (!hero.isJumping() && hero.getY() < hero.getOriginalY())
                    hero.setFalling(true);

                if (hero.isJumping())
                    hero.updateJumping();
                if (hero.isFalling())
                    hero.updateFalling();
                if (hero.isRunning() && !rightCollision) {
                    hero.setRunningCount();
                    for (ArrayList<Block> list : map.values()) {
                        for (Block block : list)
                            block.setX(-1);
                    }
                    smallCityCount -= 1;
                    if (smallCityCount < -1920)
                        smallCityCount += 1920;
                    if (count % 2 == 0) {
                        bigCityCount--;
                        if (bigCityCount < -1920)
                            bigCityCount += 1920;
                    }
                    if (count % 5 == 0) {
                        cloudsCount--;
                        if (cloudsCount < -1920)
                            cloudsCount += 1920;
                    }
                }

                repaint();

            }

            try {
                timer.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            hero.setRunning(true);
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!hero.isJumping() && !hero.isFalling()) {
                hero.setJumping(true);
                hero.setJumpingCount(0);
                hero.setOnBox(false);
            }
            repaint();
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            hero.setRunning(false);
            hero.setRunningCount(0);
            repaint();
        }
    }

    public static void main(String[] args) {
        SideScroller app = new SideScroller();
    }

}