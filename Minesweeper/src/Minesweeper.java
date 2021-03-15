import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Minesweeper extends JFrame implements ActionListener, MouseListener {

    ImageIcon[] numbers;
    ImageIcon mineIcon, flagIcon;

    JMenuBar menuBar;
    JMenu difficultyMenu;
    String[] difficultyNames;
    int[] difficultyRows, difficultyCols, difficultyMines;
    JMenuItem[] difficultyItems;

    boolean firstClick, gameOn;
    int rows, cols, mines;
    JPanel boardPanel;
    JToggleButton[][] board;

    public Minesweeper() {
        this.setTitle("Minesweeper");
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        configureIcons();
        configureMenuBar();
        configureBoard(0);
        this.setVisible(true);
    }

    public void configureIcons() {
        numbers = new ImageIcon[8];
        for (int i = 0; i < 8; i++) {
            numbers[i] = new ImageIcon((i + 1) + ".png");
            numbers[i] = new ImageIcon(numbers[i].getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        }
        mineIcon = new ImageIcon("mine.png");
        mineIcon = new ImageIcon(mineIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        flagIcon = new ImageIcon("flag.png");
        flagIcon = new ImageIcon(flagIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
    }

    public void configureMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setLayout(new GridLayout(1, 1));
        difficultyMenu = new JMenu("Difficulty");
        difficultyNames = new String[] {
                "Beginner (9x9, 10 mines)",
                "Intermediate (16x16, 40 mines)",
                "Expert (16x30, 99 mines)",
        };
        difficultyRows = new int[] {9, 16, 16};
        difficultyCols = new int[] {9, 16, 30};
        difficultyMines = new int[] {10, 40, 99};
        difficultyItems = new JMenuItem[3];
        for (int i = 0; i < difficultyItems.length; i++) {
            difficultyItems[i] = new JMenuItem(difficultyNames[i]);
            difficultyItems[i].addActionListener(this);
            difficultyMenu.add(difficultyItems[i]);
        }
        menuBar.add(difficultyMenu);
        this.add(menuBar, BorderLayout.NORTH);
        this.revalidate();
    }

    public void configureBoard(int difficulty) {
        firstClick = true;
        gameOn = true;
        rows = difficultyRows[difficulty];
        cols = difficultyCols[difficulty];
        mines = difficultyMines[difficulty];
        createBoard();
    }

    public void createBoard() {
        if (boardPanel != null)
            this.remove(boardPanel);
        boardPanel = new JPanel();
        board = new JToggleButton[rows][cols];
        boardPanel.setLayout(new GridLayout(rows, cols));
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                board[r][c] = new JToggleButton();
                board[r][c].putClientProperty("row", r);
                board[r][c].putClientProperty("col", c);
                board[r][c].putClientProperty("state", 0);
                board[r][c].setBorder(BorderFactory.createBevelBorder(0));
                board[r][c].setFocusPainted(false);
                board[r][c].addMouseListener(this);
                boardPanel.add(board[r][c]);
            }
        }
        this.add(boardPanel, BorderLayout.CENTER);
        this.setSize(cols * 40, rows * 40);
        this.revalidate();
    }

    public void setMines(int row, int col) {
        int tempMines = this.mines;
        while (tempMines > 0) {
            int r = (int) (Math.random() * rows);
            int c = (int) (Math.random() * cols);
            int state = (int) board[r][c].getClientProperty("state");
            if (state != -1 && (Math.abs(r - row) > 1 || Math.abs(c - col) > 1)) {
                board[r][c].putClientProperty("state", -1);
                tempMines--;
            }
        }
        for (int curR = 0; curR < rows; curR++) {
            for (int curC = 0; curC < cols; curC++) {
                int curS = (int) board[curR][curC].getClientProperty("state");
                if (curS != -1) {
                    int count = 0;
                    for (int r = curR - 1; r <= curR + 1; r++) {
                        for (int c = curC - 1; c <= curC + 1; c++) {
                            try {
                                int state = (int) board[r][c].getClientProperty("state");
                                if (state == -1)
                                    count++;
                            } catch (ArrayIndexOutOfBoundsException e) {

                            }
                        }
                    }
                    board[curR][curC].putClientProperty("state", count);
                }
            }
        }
    }

    public void expand(int curR, int curC) {
        if (!board[curR][curC].isSelected())
            board[curR][curC].setSelected(true);
        int curS = (int) board[curR][curC].getClientProperty("state");
        if (curS > 0) {
            board[curR][curC].setIcon(numbers[curS - 1]);
            board[curR][curC].setDisabledIcon(numbers[curS - 1]);
        }
        else {
            for (int r = curR - 1; r <= curR + 1; r++) {
                for (int c = curC - 1; c <= curC + 1; c++) {
                    try {
                        if (!board[r][c].isSelected())
                            expand(r, c);
                    } catch (ArrayIndexOutOfBoundsException e) {

                    }
                }
            }
        }
    }

    public void revealMines() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int state = (int) board[r][c].getClientProperty("state");
                if (state == -1) {
                    board[r][c].setIcon(mineIcon);
                    board[r][c].setDisabledIcon(mineIcon);
                }
                board[r][c].setEnabled(false);
            }
        }
    }

    public void checkWin() {
        int count = 0;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                int state = (int) board[r][c].getClientProperty("state");
                if (board[r][c].isSelected() && state != -1)
                    count++;
            }
        }
        if (mines == (rows * cols) - count)
            JOptionPane.showMessageDialog(null, "You won!");
    }

    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < difficultyItems.length; i++) {
            if (e.getSource() == difficultyItems[i]) {
                configureBoard(i);
                return;
            }
        }
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {
        if (!gameOn)
            return;
        int row = (int) ((JToggleButton) e.getComponent()).getClientProperty("row");
        int col = (int) ((JToggleButton) e.getComponent()).getClientProperty("col");
        int state = (int) ((JToggleButton) e.getComponent()).getClientProperty("state");
        if (e.getButton() == MouseEvent.BUTTON1 && board[row][col].isEnabled()) {
            if (firstClick) {
                setMines(row, col);
                firstClick = false;
            }
            if (state == -1) {
                gameOn = false;
                board[row][col].setIcon(mineIcon);
                board[row][col].setContentAreaFilled(false);
                board[row][col].setOpaque(true);
                board[row][col].setBackground(Color.RED);
                revealMines();
                JOptionPane.showMessageDialog(null, "You lost!");
            }
            else {
                expand(row, col);
                checkWin();
            }
        }
        else if (e.getButton() == MouseEvent.BUTTON3 && !board[row][col].isSelected()) {
            boolean unflagged = board[row][col].getIcon() == null;
            board[row][col].setIcon(unflagged ? flagIcon : null);
            board[row][col].setDisabledIcon(unflagged ? flagIcon : null);
            board[row][col].setEnabled(!unflagged);
        }
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public static void main(String[] args) {
        Minesweeper app = new Minesweeper();
    }

}