import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.text.DecimalFormat;

public class ControlBar {

    private EmptyBorder border = null;
    private DecimalFormat decimalFormat;

    private String name;
    private int initial;
    private int min;
    private int max;
    private float divisor;

    private JScrollBar bar;
    private float value;
    private JLabel label;

    public ControlBar(String name, int initial, int min, int max, float divisor) {

        border = new EmptyBorder(0, 0, 0, 24);
        decimalFormat = new DecimalFormat("#.000");

        this.name = name;
        this.initial = initial;
        this.min = min;
        this.max = max;
        this.divisor = divisor;

        bar = new JScrollBar(JScrollBar.HORIZONTAL, initial, 0, min, max);
        value = bar.getValue() / divisor;
        label = new JLabel(name + ": " + decimalFormat.format(value));
        label.setBorder(border);

    }

    public void update() {
        value = bar.getValue() / divisor;
        label.setText(name + ": " + decimalFormat.format(value));
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

}
