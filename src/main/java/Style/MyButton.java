package Style;

import javax.swing.*;

public class MyButton extends JButton {
    public MyButton() {
        super();
        setContentAreaFilled(false);
    }

    public MyButton(Icon icon) {
        super(icon);
        setContentAreaFilled(false);
    }

    public MyButton(String text) {
        super(text);
        setContentAreaFilled(false);
    }

    public MyButton(Action a) {
        super(a);
        setContentAreaFilled(false);
    }

    public MyButton(String text, Icon icon) {
        super(text, icon);
        setContentAreaFilled(false);
    }
}
