import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class SoundMatrix extends JPanel implements Runnable, ActionListener, AdjustmentListener {

    JFrame frame;

    String[] clipNames;
    Clip[] clips;

    int columns, gap;
    Font font;
    Dimension dimension;
    Insets insets;

    JMenuBar menuBar;

    JMenu fileMenu;
    JMenuItem saveItem, loadItem;
    JFileChooser fileChooser;

    JMenu songsMenu;
    JMenuItem[] songItems;

    JMenu instrumentsMenu;
    String[] instrumentNames;
    JMenuItem[] instrumentItems;

    JButton playbackBtn;
    boolean playing;
    int col;

    JButton randomBtn;

    JButton addBtn;
    JButton removeBtn;

    JButton clearBtn;

    JScrollPane btnPane;
    JPanel btnPanel;
    JToggleButton[][] buttons;

    JPanel tempoPanel;
    JLabel tempoLabel;
    JScrollBar tempoBar;
    int tempo;

    public SoundMatrix() {

        // creating JFrame
        frame = new JFrame("Sound Matrix");
        frame.add(this);
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // setting up audio clips
        clipNames = new String[] { "C0",
                "B1", "ASharp1", "A1", "GSharp1", "G1", "FSharp1", "F1", "E1", "DSharp1", "D1", "CSharp1", "C1",
                "B2", "ASharp2", "A2", "GSharp2", "G2", "FSharp2", "F2", "E2", "DSharp2", "D2", "CSharp2", "C2",
                "B3", "ASharp3", "A3", "GSharp3", "G3", "FSharp3", "F3", "E3", "DSharp3", "D3", "CSharp3", "C3",
        };
        clips = new Clip[clipNames.length];

        // settings
        columns = 250;
        gap = 3;
        font = new Font("Times New Roman", Font.PLAIN, 10);
        dimension = new Dimension(30, 30);
        insets = new Insets(0, 0, 0, 0);

        // file menu
        fileMenu = new JMenu("File");
        saveItem = new JMenuItem("Save");
        saveItem.addActionListener(this);
        fileMenu.add(saveItem);
        loadItem = new JMenuItem("Load");
        loadItem.addActionListener(this);
        fileMenu.add(loadItem);
        fileChooser = new JFileChooser(System.getProperty("user.dir"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));

        // songs menu
        songsMenu = new JMenu("Presets");
        File folder = new File("Songs");
        File[] files = folder.listFiles();
        ArrayList<String> songNames = new ArrayList<String>();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isFile()) {
                int index = file.getName().lastIndexOf(".");
                String extension = file.getName().substring(index + 1);
                if (extension.equals("txt")) {
                    String name = file.getName().substring(0, index);
                    songNames.add(name);
                }
            }
        }
        songItems = new JMenuItem[songNames.size()];
        for (int i = 0; i < songItems.length; i++) {
            songItems[i] = new JMenuItem(songNames.get(i));
            songItems[i].addActionListener(this);
            songsMenu.add(songItems[i]);
        }

        // instruments menu
        instrumentsMenu = new JMenu("Instruments");
        instrumentNames = new String[] { "Bell", "Glockenspiel", "Marimba", "Oboe", "Oh_Ah", "Piano" };
        instrumentItems = new JMenuItem[instrumentNames.length];
        for (int i = 0; i < instrumentItems.length; i++) {
            instrumentItems[i] = new JMenuItem(instrumentNames[i]);
            instrumentItems[i].addActionListener(this);
            instrumentsMenu.add(instrumentItems[i]);
        }
        changeInstrument(instrumentNames[0]);

        // playback button
        playbackBtn = new JButton("Play");
        playbackBtn.addActionListener(this);
        playing = false;
        col = 0;

        // random button
        randomBtn = new JButton("Random");
        randomBtn.addActionListener(this);

        // add button
        addBtn = new JButton("Add");
        addBtn.addActionListener(this);

        // remove button
        removeBtn = new JButton("Remove");
        removeBtn.addActionListener(this);

        // clear button
        clearBtn = new JButton("Clear");
        clearBtn.addActionListener(this);

        // menu bar
        menuBar = new JMenuBar();
        menuBar.setLayout(new GridLayout(1, 8));
        menuBar.add(fileMenu);
        menuBar.add(songsMenu);
        menuBar.add(instrumentsMenu);
        menuBar.add(playbackBtn);
        menuBar.add(randomBtn);
        menuBar.add(addBtn);
        menuBar.add(removeBtn);
        menuBar.add(clearBtn);
        frame.add(menuBar, BorderLayout.NORTH);

        // toggle buttons
        btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(clipNames.length, columns, gap, gap));
        setUpButtons();
        btnPane = new JScrollPane(btnPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        frame.add(btnPane, BorderLayout.CENTER);

        // tempo
        tempoBar = new JScrollBar(JScrollBar.HORIZONTAL, 200, 0, 50, 500);
        tempoBar.addAdjustmentListener(this);
        tempo = tempoBar.getValue();
        tempoLabel = new JLabel(String.format("%s: %6s", "Tempo", tempo));
        tempoPanel = new JPanel();
        tempoPanel.setLayout(new BorderLayout());
        tempoPanel.add(tempoLabel, BorderLayout.WEST);
        tempoPanel.add(tempoBar, BorderLayout.CENTER);
        frame.add(tempoPanel, BorderLayout.SOUTH);

        // making frame visible
        frame.setVisible(true);

        // starting thread
        Thread thread = new Thread(this);
        thread.start();

    }

    public void setUpButtons() {
        buttons = new JToggleButton[clipNames.length][columns];
        for (int r = 0; r < buttons.length; r++) {
            for (int c = 0; c < buttons[r].length; c++) {
                buttons[r][c] = new JToggleButton();
                buttons[r][c].setFont(font);
                buttons[r][c].setText(clipNames[r].replaceAll("Sharp", "#"));
                buttons[r][c].setPreferredSize(dimension);
                buttons[r][c].setMargin(insets);
                btnPanel.add(buttons[r][c]);
            }
        }
        frame.revalidate();
    }

    public void random() {
        clear(true);
        for (int r = 0; r < buttons.length; r++) {
            for (int c = 0; c < buttons[r].length; c++) {
                int num = (int)(Math.random() * 5);
                if (num == 0)
                    buttons[r][c].setSelected(true);
            }
        }
    }

    public void setNotes(Character[][] notes) {
        for (int r = 0; r < buttons.length; r++) {
            for (int c = 0; c < buttons[r].length; c++)
                buttons[r][c].setSelected(notes[r][c] == 'x');
        }
    }

    public void save() {
        try {
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String name = file.getAbsolutePath().replaceAll(".txt", "");
                String output = "";
                for (int r = -1; r < buttons.length; r++) {
                    if (r == -1) {
                        output += tempo;
                        for (int c = 0; c < buttons[0].length; c++)
                            output += " ";
                    } else {
                        for (int c = 0; c < buttons[r].length; c++)
                            output += buttons[r][c].isSelected() ? "x" : "-";
                    }
                    output += "\n";
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(name + ".txt"));
                writer.write(output);
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            loadFile(file);
        }
    }

    public void loadFile(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String text = reader.readLine();
            tempoBar.setValue(Integer.parseInt(text.substring(0, 3)));
            Character[][] notes = new Character[buttons.length][columns];
            int r = 0;
            while ((text = reader.readLine()) != null) {
                if (r == 0)
                    columns = text.length();
                for (int c = 0; c < notes[r].length; c++) {
                    notes[r][c] = '-';
                    if (c < text.length())
                        notes[r][c] = text.charAt(c);
                }
                r++;
            }
            setNotes(notes);
            adjustLength();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeInstrument(String instrument) {
        for (int r = 0; r < clipNames.length; r++) {
            try {
                URL url = new File(instrument + "\\" + instrument + " - " + clipNames[r] + ".wav").toURI().toURL();
                AudioInputStream stream = AudioSystem.getAudioInputStream(url);
                clips[r] = AudioSystem.getClip();
                clips[r].open(stream);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    public void adjustLength() {
        btnPanel.removeAll();
        JToggleButton[][] temp = buttons.clone();
        setUpButtons();
        for (int r = 0; r < buttons.length; r++) {
            for (int c = 0; c < buttons[r].length; c++) {
                try {
                    buttons[r][c].setSelected(temp[r][c].isSelected());
                } catch (ArrayIndexOutOfBoundsException e) {

                }
            }
        }
        clear(false);
    }

    public void clear(boolean deleteNotes) {
        reset();
        playing = false;
        col = 0;
        playbackBtn.setText("Play");
        if (deleteNotes) {
            for (int r = 0; r < buttons.length; r++) {
                for (int c = 0; c < buttons[r].length; c++)
                    buttons[r][c].setSelected(false);
            }
        }
        btnPane.getHorizontalScrollBar().setValue(0);
    }

    public void reset() {
        for (int r = 0; r < buttons.length; r++) {
            if (buttons[r][col].isSelected()) {
                clips[r].stop();
                clips[r].setFramePosition(0);
                buttons[r][col].setForeground(Color.BLACK);
            }
        }
    }

    public void autoScroll() {
        int delta = btnPane.getHorizontalScrollBar().getMaximum() - btnPane.getHorizontalScrollBar().getMinimum();
        btnPane.getHorizontalScrollBar().setValue(delta * col / columns);
    }

    public void adjustmentValueChanged(AdjustmentEvent e) {
        if (e.getSource() == tempoBar) {
            tempo = tempoBar.getValue();
            tempoLabel.setText(String.format("%s: %6s", "Tempo", tempo));
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveItem)
            save();
        else if (e.getSource() == loadItem)
            load();
        else if (e.getSource() instanceof JMenuItem) {
            for (int i = 0; i < songItems.length; i++) {
                if (e.getSource() == songItems[i])
                    loadFile(new File("Songs/" + songItems[i].getText() + ".txt"));
            }
            for (int i = 0; i < instrumentItems.length; i++) {
                if (e.getSource() == instrumentItems[i]) {
                    changeInstrument(instrumentNames[i]);
                    clear(false);
                    break;
                }
            }
        }
        else if (e.getSource() == playbackBtn) {
            playing = !playing;
            playbackBtn.setText(playing ? "Pause" : "Play");
        }
        else if (e.getSource() == randomBtn) {
            random();
        }
        else if (e.getSource() == addBtn) {
            columns++;
            adjustLength();
        }
        else if (e.getSource() == removeBtn) {
            columns--;
            adjustLength();
        }
        else if (e.getSource() == clearBtn)
            clear(true);
    }

    public void run() {
        do {
            try {
                if (!playing)
                    Thread.sleep(0);
                else {
                    for (int r = 0; r < buttons.length; r++) {
                        if (buttons[r][col].isSelected()) {
                            clips[r].start();
                            buttons[r][col].setForeground(Color.YELLOW);
                        }
                    }
                    Thread.sleep(tempo);
                    reset();
                    col++;
                    if (col == columns)
                        col = 0;
                    autoScroll();
                }
            } catch (Exception e) {

            }
        } while (true);
    }

    public static void main(String[] args) {
        SoundMatrix app = new SoundMatrix();
    }

}