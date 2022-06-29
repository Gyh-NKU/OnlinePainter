package OnlineMode;

import Main.ModeSelectFream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.*;
import java.util.logging.Logger;
//import Net.User;

public class LoginBoard extends JFrame implements ActionListener {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/online_printer";
    private static final String JDBC_USER = "NKGyh";
    private static final String JDBC_PASSWORD = "3.1415926";
    JLabel end;
    JLabel userName;
    JLabel passwd;
    JTextField endField;
    JTextField nameField;
    JTextField passwdField;
    JButton enter;
    JButton enter2;
    JButton register;
    JButton exit;
    String playerName;
    String playerPasswd;
    String ip;
    Logger logger = Logger.getLogger("登录界面");
    JPanel buttons;
    JPanel ipPanel;
    JPanel passwdPanel;
    JPanel namePanel;

    public LoginBoard() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(500, 300);
        setPreferredSize(new Dimension(300, 200));
        setLayout(new GridLayout(4, 1));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new ModeSelectFream();
                dispose();
            }
        });
        initialize();
        setVisible(true);
    }

    private void initialize() {
        end = new JLabel("服务器ip：");
        end.setVisible(true);
        userName = new JLabel("用户名：");
        userName.setVisible(true);
        passwd = new JLabel("密码：");
        endField = new JTextField("127.0.0.1");
        nameField = new JTextField("xiaoming");
        passwdField = new JPasswordField("123456");
        buttons = new JPanel();
        ipPanel = new JPanel(new GridLayout(1, 2));
        namePanel = new JPanel(new GridLayout(1, 2));
        passwdPanel = new JPanel(new GridLayout(1, 2));
        enter = new JButton("画板");
        enter.setVisible(true);
        enter.addActionListener(this);
        enter2 = new JButton("你画我猜");
        enter2.addActionListener(this);
        register = new JButton("注册");
        register.setVisible(true);
        register.addActionListener(this);
        exit = new JButton("返回");
        exit.setVisible(true);
        exit.addActionListener(this);
        ipPanel.add(end);
        ipPanel.add(endField);
        namePanel.add(userName);
        namePanel.add(nameField);
        passwdPanel.add(passwd);
        passwdPanel.add(passwdField);
        buttons.add(enter);
        buttons.add(enter2);
        buttons.add(register);
        buttons.add(exit);
        add(ipPanel);
        add(namePanel);
        add(passwdPanel);
        add(buttons);
        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == enter||e.getSource() == enter2) {
            playerName = nameField.getText();
            playerPasswd = passwdField.getText();
            ip = endField.getText();
            if (playerName.equals("")) {
                JOptionPane.showMessageDialog(this, "用户名不能为空");
                logger.info("登陆失败：用户名为空");
            } else if (ip.equals("")) {
                JOptionPane.showMessageDialog(this, "IP不能为空");
                logger.info("登陆失败：ip为空");
            } else {
                try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
                    try (PreparedStatement ps = conn.prepareStatement("SELECT user_id FROM users WHERE user_name=? AND passwd=?")) {
                        ps.setObject(1, playerName);
                        ps.setObject(2, playerPasswd);
                        try (ResultSet rs = ps.executeQuery()) {
                            if (rs.next()) {
                                int id = rs.getInt("user_id");
                                setTitle("连接中...");
                                try {
                                    if(e.getSource() == enter)
                                        new GameFrame(InetAddress.getByName(ip), playerName, 6666, 6667);
                                    if(e.getSource() == enter2)
                                        new GameFrame(InetAddress.getByName(ip),playerName,6666,6668);
                                    dispose();
                                    JOptionPane.showMessageDialog(this, "登录成功");
                                } catch (IOException exp) {
                                    JOptionPane.showMessageDialog(this, "连接失败，服务器未响应", "连接失败", JOptionPane.ERROR_MESSAGE);
                                    setTitle("登录界面");
                                }
//                                User user=new User(playername, ip);
//                                new NetModeWindow(user);
                            } else {
                                JOptionPane.showMessageDialog(this, "密码错误");
                            }
                        }
                    }
                } catch (SQLException err) {
                    err.printStackTrace();
                }

            }
        } else if (e.getSource() == exit) {
            dispose();
            new ModeSelectFream();
        } else if (e.getSource() == register) {
            new RegisterBoard(this);
        }
    }
}
