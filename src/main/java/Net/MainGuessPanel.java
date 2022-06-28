package Net;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class MainGuessPanel extends JPanel {
    PaintPanel paintPanel;
    ChatPanel chatPanel;
    Socket paintSocket;
    Socket guessSocket;
    public MainGuessPanel(InetAddress ip, String name, int paintPort, int chatPort) throws IOException{
        paintSocket = new Socket(ip,paintPort);
        guessSocket = new Socket(ip,chatPort);
        setBounds(100, 50, 1400, 700);
        paintPanel = new PaintPanel(paintSocket);
        chatPanel = new ChatPanel(guessSocket, name);
        setLayout(new BorderLayout());
        add(paintPanel, BorderLayout.CENTER);
        add(chatPanel, BorderLayout.EAST);
        paintPanel.p.showHideChat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!chatPanel.isVisible()) {
                    chatPanel.setVisible(true);
                    setSize(getWidth() + chatPanel.getWidth(),
                            getHeight());
                    paintPanel.p.showHideChat.setDirection(SwingConstants.WEST);
                } else {
                    chatPanel.setVisible(false);
                    setSize(getWidth() - chatPanel.getWidth(),
                            getHeight());
                    paintPanel.p.showHideChat.setDirection(SwingConstants.EAST);
                }
            }
        });
        setVisible(true);
    }

}
