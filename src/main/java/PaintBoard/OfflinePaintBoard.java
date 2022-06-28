package PaintBoard;

import Main.ModeSelectFream;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OfflinePaintBoard extends JFrame {
    public OfflinePaintBoard(){
        super("离线画板");
        setContentPane(new PaintBoard());
        setBounds(300,100,1000,700);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            new ModeSelectFream();
            dispose();
            }
        });
        setVisible(true);
    }

}
