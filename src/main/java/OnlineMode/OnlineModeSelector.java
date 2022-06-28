package OnlineMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Objects;

public class OnlineModeSelector extends JFrame implements ActionListener {
    String ip;
    String name;
    JLabel title;
    JPanel titleArea;
    JButton offlineBtn;
    JButton onlineBtn;
    JPanel btnArea;

    public OnlineModeSelector(String ip, String name) throws IOException{
        super("选择模式");
        this.ip = ip;
        this.name = name;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(500, 200, 300, 400);
        title = new JLabel("画图");
        title.setFont(new Font("黑体", Font.BOLD, 30));
        titleArea = new JPanel();
        titleArea.add(title);
        add(titleArea, BorderLayout.NORTH);
        offlineBtn = new JButton("在线画板");
        offlineBtn.setFont(new Font("方正标宋", Font.PLAIN, 20));
        offlineBtn.addActionListener(this);
        onlineBtn = new JButton("你画我猜游戏");
        onlineBtn.setFont(new Font("方正标宋", Font.PLAIN, 20));
        onlineBtn.addActionListener(this);
        btnArea = new JPanel(new GridLayout(0, 1));
        btnArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        btnArea.add(offlineBtn);
        btnArea.add(onlineBtn);
        add(btnArea, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if (Objects.equals(btn.getText(), "在线画板")) {
            dispose();
            try {
                new GameFrame(InetAddress.getByName(ip), name, 6666, 6667);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (Objects.equals(btn.getText(), "你画我猜游戏")) {
            dispose();
            try {
                new GameFrame(InetAddress.getByName(ip), name, 6666, 6668);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
