package OnlineMode;

import Net.MainGuessPanel;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;

public class GameFrame extends JFrame {
    public GameFrame(InetAddress ip, String name, int paintPort, int gamePort) throws IOException {
        setBounds(100, 50, 1400, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new MainGuessPanel(ip, name, paintPort, gamePort));
        setVisible(true);
    }
}
