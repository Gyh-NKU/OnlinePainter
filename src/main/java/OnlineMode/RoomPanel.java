package OnlineMode;

import javax.swing.*;
import java.awt.*;

public class RoomPanel extends JPanel {
    Room room;
    JLabel name;
    JLabel player;
    JLabel watcher;
    JLabel status;
    JButton enterRoomAsPlayer;
    JButton enterRoomAsWatcher;

    public RoomPanel(String roomName) {
        room = new Room(roomName);
        setPreferredSize(new Dimension(600, 60));
        setLayout(new GridLayout(1, 6));

        enterRoomAsPlayer = new JButton("加入游戏");
        enterRoomAsWatcher = new JButton("观战");
        name = new JLabel(roomName);
        player = new JLabel("0");
        watcher = new JLabel("0");
        status = new JLabel("0");
        name.setHorizontalAlignment(SwingConstants.CENTER);
        player.setHorizontalAlignment(SwingConstants.CENTER);
        watcher.setHorizontalAlignment(SwingConstants.CENTER);
        status.setHorizontalAlignment(SwingConstants.CENTER);

        add(name);
        add(player);
        add(watcher);
        add(status);
        add(enterRoomAsPlayer);
        add(enterRoomAsWatcher);
        setVisible(true);
    }

    public JLabel getPlayer() {
        return player;
    }

    public JLabel getStatus() {
        return status;
    }

    public JLabel getWatcher() {
        return watcher;
    }

    public void setNameLabel(JLabel name) {
        this.name = name;
    }

    public void setPlayer(JLabel player) {
        this.player = player;
    }

    public void setWatcher(JLabel watcher) {
        this.watcher = watcher;
    }

    public void setStatus(JLabel status) {
        this.status = status;
    }

    public JLabel getNameLabel() {
        return name;
    }

    public JButton getEnterRoomAsPlayer() {
        return enterRoomAsPlayer;
    }

    public JButton getEnterRoomAsWatcher() {
        return enterRoomAsWatcher;
    }
}
