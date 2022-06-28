package Net;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class GuessPanel extends ChatPanel{
    public GuessPanel(Socket chatSocket, String name) {
        super(chatSocket, name);
    }

    public static void main(String[] args) {
        String name = JOptionPane.showInputDialog("请输入名称");
        try {
            JFrame f = new JFrame();
            f.add(new GuessPanel(new Socket(InetAddress.getLocalHost(),6668),name));
            f.pack();
            f.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
