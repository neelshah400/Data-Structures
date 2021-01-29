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

    JButton northBtn, southBtn, eastBtn, westBtn, resetBtn;
    ArrayList<JButton> btnList;

    JMenu fontMenu, fontSizeMenu, textColorMenu, bgColorMenu, outlineColorMenu;
    ArrayList<JMenu> menuList;

    String[] fontNames, fontSizeNames, textColorNames, bgColorNames, outlineColorNames;
    ArrayList<String[]> namesList;

    Font[][] fonts;

    JMenuItem[] fontItems, fontSizeItems, textColorItems, bgColorItems, outlineColorItems;
    ArrayList<JMenuItem[]> itemsList;

    JTextArea textArea;

    int fontIndex, fontSizeIndex, textColorIndex, bgColorIndex, outlineColorIndex;

    public GUITask() {

        // creating JFrame
        frame = new JFrame("GUI Task");
        frame.add(this);
        frame.setSize(1400, 800);
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
        for (JButton btn : btnList) {
            btn.addActionListener(this);
            try {
                btn.setBorder(new LineBorder(Color.getColor(outlineColorNames[0])));
            } catch (Exception e) {
                // Invalid color
            }
        }

        // creating JMenu for each value
        fontMenu = new JMenu("Font");
        fontSizeMenu = new JMenu("Font Size");
        textColorMenu = new JMenu("Text Color");
        bgColorMenu = new JMenu("Background Color");
        outlineColorMenu = new JMenu("Outline Color");
        menuList = new ArrayList<JMenu>();
        menuList.add(fontMenu);
        menuList.add(fontSizeMenu);
        menuList.add(textColorMenu);
        menuList.add(bgColorMenu);
        menuList.add(outlineColorMenu);

        // creating name arrays
        fontNames = new String[] {"Times New Roman", "Arial", "Consolas"};
        fontSizeNames = new String[] {"12", "18", "24"};
        textColorNames = new String[] {"Black", "Blue", "Random"};
        bgColorNames = new String[] {"Purple", "Yellow", "Random"};
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

        // add to layout
        addToLayout(0);

        // making JFrame visible
        frame.setVisible(true);

    }

    public void addToLayout(int dir) {

        // button panel
        btnPanel = new JPanel();
        btnPanelLayout = new GridLayout(1, 4);
        btnPanel.setLayout(btnPanelLayout);
        for (JButton btn : btnList)
            btnPanel.add(btn);

        // menu bar
        menuBar = new JMenuBar();
        menuLayout = new GridLayout(1, 6);
        menuBar.setLayout(menuLayout);
        for (JMenu menu : menuList)
            menuBar.add(menu);
        menuBar.add(resetBtn);

        // big panel
        bigPanel = new JPanel();
        bigPanelLayout = new GridLayout(1, 2);
        bigPanel.setLayout(bigPanelLayout);
        bigPanel.add(btnPanel);
        bigPanel.add(menuBar);

        // text area
        textArea = new JTextArea();
        textArea.setBackground(Color.getColor(bgColorNames[bgColorIndex]));
        textArea.setForeground(Color.getColor(textColorNames[textColorIndex]));
        textArea.setFont(fonts[fontIndex][fontSizeIndex]);

        // frame
        frame.add(bigPanel, BorderLayout.NORTH);
        frame.add(textArea, BorderLayout.CENTER);

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() instanceof JButton) {
            if (e.getSource() == resetBtn) {

            }
            else {
                int index = btnList.indexOf(e.getSource());
            }
        }
        else if (e.getSource() instanceof JMenuItem) {
            int index = itemsList.indexOf(e.getSource());
        }

    }

    public static void main(String[] args) {
        GUITask app = new GUITask();
    }

}