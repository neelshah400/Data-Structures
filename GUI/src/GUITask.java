import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class GUITask extends JPanel implements ActionListener {

    JFrame frame;
    JPanel btnPanel, bigPanel;
    JMenuBar menuBar;
    GridLayout btnPanelLayout, menuLayout, bigPanelLayout;
    String layoutDir;

    JButton northBtn, southBtn, eastBtn, westBtn, resetBtn;
    ArrayList<JButton> btnList;
    ArrayList<JButton> allButtons;

    JMenu fontMenu, fontSizeMenu, textColorMenu, bgColorMenu, outlineColorMenu;
    ArrayList<JMenu> menuList;

    String[] fontNames, fontSizeNames, textColorNames, bgColorNames, outlineColorNames;
    ArrayList<String[]> namesList;

    Font[][] fonts;
    Color[] textColors, bgColors, outlineColors;

    JMenuItem[] fontItems, fontSizeItems, textColorItems, bgColorItems, outlineColorItems;
    ArrayList<JMenuItem[]> itemsList;

    JTextArea textArea;

    int fontIndex, fontSizeIndex, textColorIndex, bgColorIndex, outlineColorIndex;

    public GUITask() {

        // creating JFrame
        frame = new JFrame("GUI Task");
        frame.add(this);
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // setting up JButton for each direction
        northBtn = new JButton("North");
        southBtn = new JButton("South");
        eastBtn = new JButton("East");
        westBtn = new JButton("West");
        btnList = new ArrayList<JButton>();
        btnList.add(northBtn);
        btnList.add(southBtn);
        btnList.add(eastBtn);
        btnList.add(westBtn);
        for (JButton btn : btnList)
            btn.addActionListener(this);

        // creating JMenu for each value
        fontMenu = new JMenu("Font");
        fontSizeMenu = new JMenu("Font Size");
        textColorMenu = new JMenu("Text Color");
        bgColorMenu = new JMenu("Bg Color");
        outlineColorMenu = new JMenu("Outline Color");
        menuList = new ArrayList<JMenu>();
        menuList.add(fontMenu);
        menuList.add(fontSizeMenu);
        menuList.add(textColorMenu);
        menuList.add(bgColorMenu);
        menuList.add(outlineColorMenu);

        // creating name arrays
        fontNames = new String[] {"Arial", "Times New Roman", "Consolas"};
        fontSizeNames = new String[] {"14", "18", "24", "30", "36", "48", "60", "72", "96"};
        textColorNames = new String[] {"Black", "Blue", "Random"};
        bgColorNames = new String[] {"Cyan", "Yellow", "Random"};
        outlineColorNames = new String[] {"None", "Orange", "Green", "Random"};
        namesList = new ArrayList<String[]>();
        namesList.add(fontNames);
        namesList.add(fontSizeNames);
        namesList.add(textColorNames);
        namesList.add(bgColorNames);
        namesList.add(outlineColorNames);

        // setting current indices
        fontIndex = 0;
        fontSizeIndex = 0;
        textColorIndex = 0;
        bgColorIndex = 0;
        outlineColorIndex = 0;

        // creating Font array
        fonts = new Font[fontNames.length][fontSizeNames.length];
        for (int i = 0; i < fonts.length; i++) {
            for (int j = 0; j < fonts[i].length; j++)
                fonts[i][j] = new Font(fontNames[i], Font.PLAIN, Integer.parseInt(fontSizeNames[j]));
        }

        // creating Color arrays
        textColors = new Color[] {Color.BLACK, Color.BLUE, null};
        bgColors = new Color[] {Color.CYAN, Color.YELLOW, null};
        outlineColors = new Color[] {null, Color.ORANGE, Color.GREEN, null};

        // creating JMenuItem arrays
        fontItems = new JMenuItem[fontNames.length];
        fontSizeItems = new JMenuItem[fontSizeNames.length];
        textColorItems = new JMenuItem[textColorNames.length];
        bgColorItems = new JMenuItem[bgColorNames.length];
        outlineColorItems = new JMenuItem[outlineColorNames.length];
        itemsList = new ArrayList<JMenuItem[]>();
        itemsList.add(fontItems);
        itemsList.add(fontSizeItems);
        itemsList.add(textColorItems);
        itemsList.add(bgColorItems);
        itemsList.add(outlineColorItems);

        // setting up menus
        for (int i = 0; i < menuList.size(); i++) {
            JMenu menu = menuList.get(i);
            String[] names = namesList.get(i);
            JMenuItem[] items = itemsList.get(i);
            for (int j = 0; j < items.length; j++) {
                items[j] = new JMenuItem(names[j]);
                if (items == fontItems)
                    items[j].setFont(fonts[j][0]);
                items[j].addActionListener(this);
                menu.add(items[j]);
            }
        }

        // creating reset button
        resetBtn = new JButton("Reset");
        resetBtn.addActionListener(this);
        resetBtn.setBorder(new LineBorder(Color.getColor(outlineColorNames[0])));

        // creating list of all buttons
        allButtons = new ArrayList<JButton>(btnList);
        allButtons.add(resetBtn);

        // button panel
        btnPanel = new JPanel();
        for (JButton btn : btnList)
            btnPanel.add(btn);

        // menu bar
        menuBar = new JMenuBar();
        for (JMenu menu : menuList)
            menuBar.add(menu);
        menuBar.add(resetBtn);

        // big panel
        bigPanel = new JPanel();
        bigPanel.add(btnPanel);
        bigPanel.add(menuBar);

        // text area
        textArea = new JTextArea();
        frame.add(textArea, BorderLayout.CENTER);

        // adjustments
        changeValues(true, true, true, true);
        changeLayout(0);

        // making JFrame visible
        frame.setVisible(true);

    }

    public void changeLayout(int dir) {
        if (dir == 0 || dir == 1) {
            btnPanelLayout = new GridLayout(1, 4);
            menuLayout = new GridLayout(1, 6);
            bigPanelLayout = new GridLayout(1, 2);
            layoutDir = dir == 0 ? BorderLayout.NORTH : BorderLayout.SOUTH;
        }
        else if (dir == 2 || dir == 3) {
            btnPanelLayout = new GridLayout(4, 1);
            menuLayout = new GridLayout(6, 1);
            bigPanelLayout = new GridLayout(2, 1);
            layoutDir = dir == 2 ? BorderLayout.EAST : BorderLayout.WEST;
        }
        btnPanel.setLayout(btnPanelLayout);
        menuBar.setLayout(menuLayout);
        bigPanel.setLayout(bigPanelLayout);
        frame.add(bigPanel, layoutDir);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            if (e.getSource() == resetBtn) {
                fontIndex = 0;
                fontSizeIndex = 0;
                textColorIndex = 0;
                bgColorIndex = 0;
                outlineColorIndex = 0;
                changeValues(true, true, true, true);
                changeLayout(0);
            }
            else {
                int index = btnList.indexOf(e.getSource());
                frame.remove(bigPanel);
                changeLayout(index);
                frame.revalidate();
            }
        }
        else if (e.getSource() instanceof JMenuItem) {
            for (JMenuItem[] items : itemsList) {
                ArrayList<JMenuItem> itemList = new ArrayList<JMenuItem>(Arrays.asList(items));
                if (itemList.contains(e.getSource())) {
                    int index = itemList.indexOf(e.getSource());
                    if (items == fontItems) {
                        fontIndex = index;
                        changeValues(true, false, false, false);
                    }
                    else if (items == fontSizeItems) {
                        fontSizeIndex = index;
                        changeValues(true, false, false, false);
                    }
                    else if (items == textColorItems) {
                        textColorIndex = index;
                        changeValues(false, true, false, false);
                    }
                    else if (items == bgColorItems) {
                        bgColorIndex = index;
                        changeValues(false, false, true, false);
                    }
                    else if (items == outlineColorItems) {
                        outlineColorIndex = index;
                        changeValues(false, false, false, true);
                    }
                }
            }
        }
    }

    public void changeValues(boolean changeFont, boolean changeTextColor, boolean changeBgColor, boolean changeOutlineColor) {
        if (changeFont) {
            textArea.setFont(fonts[fontIndex][fontSizeIndex]);
            changeAllFonts();
        }
        if (changeTextColor)
            textArea.setForeground(textColorIndex == textColors.length - 1 ? getRandomColor() : textColors[textColorIndex]);
        if (changeBgColor)
            textArea.setBackground(bgColorIndex == bgColors.length - 1 ? getRandomColor() : bgColors[bgColorIndex]);
        if (changeOutlineColor) {
            LineBorder border;
            if (outlineColorIndex == 0)
                border = null;
            else if (outlineColorIndex == outlineColors.length - 1)
                border = new LineBorder(getRandomColor());
            else
                border = new LineBorder(outlineColors[outlineColorIndex]);
            for (JButton btn : allButtons)
                btn.setBorder(border);
        }
    }

    public void changeAllFonts() {
        Font font = fonts[fontIndex][0];
        for (JButton btn : allButtons)
            btn.setFont(font);
        for (JMenu menu : menuList)
            menu.setFont(font);
        for (JMenuItem[] items : itemsList) {
            if (items != fontItems) {
                for (JMenuItem item : items)
                    item.setFont(font);
            }
        }
    }

    public Color getRandomColor() {
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);
        return new Color(r, g, b);
    }

    public static void main(String[] args) {
        GUITask app = new GUITask();
    }

}