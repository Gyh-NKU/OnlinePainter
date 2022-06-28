package OnlineMode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterBoard extends LoginBoard implements ActionListener {


    LoginBoard parent;
    JButton register;
    JButton exit;

    public RegisterBoard(LoginBoard par) {
        super();
        enter.setVisible(false);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == register){

        }else if(e.getSource() == exit){
            dispose();
            parent.setEnabled(true);
        }

    }
}
