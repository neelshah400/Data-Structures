import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ControlBar {

    private EmptyBorder border;

    private String name;
    private int initial;
    private int min;
    private int max;
    private float divisor;

    private JScrollBar bar;
    private float value;
    private JLabel label;

    public ControlBar(String name, int initial, int min, int max, float divisor) {

        border = new EmptyBorder(0, 0, 0, 32);

        this.name = name;
        this.initial = initial;
        this.min = min;
        this.max = max;
        this.divisor = divisor;

        bar = new JScrollBar(JScrollBar.HORIZONTAL, initial, 0, min, max);
        value = bar.getValue() / divisor;
        label = new JLabel(name + ": " + value);
        label.setBorder(border);

    }

    public void update() {
        value = bar.getValue() / divisor;
        label.setText(name + ": " + value);
    }

    public JScrollBar getBar() {
        return bar;
    }

    public float getValue() {
        return value;
    }

    public JLabel getLabel() {
        return label;
    }

    public int getInitial() {
        return initial;
    }

}
