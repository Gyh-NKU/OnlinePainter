package Main;

import OnlineMode.LoginBoard;
import PaintBoard.OfflinePaintBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ModeSelectFream extends JFrame implements ActionListener {
    JLabel title;
    JPanel titleArea;
    JButton offlineBtn;
    JButton onlineBtn;
    JPanel btnArea;
    public ModeSelectFream(){
        super("选择模式");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(500,200,300,400);
        title = new JLabel("画图");
        title.setFont(new Font("黑体",Font.BOLD,30));
        titleArea = new JPanel();
        titleArea.add(title);
        add(titleArea, BorderLayout.NORTH);
        offlineBtn = new JButton("离线模式");
        offlineBtn.setFont(new Font("方正标宋",Font.PLAIN,20));
        offlineBtn.addActionListener(this);
        onlineBtn = new JButton("在线模式");
        onlineBtn.setFont(new Font("方正标宋",Font.PLAIN,20));
        onlineBtn.addActionListener(this);
        btnArea = new JPanel(new GridLayout(0,1));
        btnArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        btnArea.add(offlineBtn);
        btnArea.add(onlineBtn);
        add(btnArea,BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if(Objects.equals(btn.getText(), "离线模式")){
            dispose();
            new OfflinePaintBoard();
        }else if(Objects.equals(btn.getText(),"在线模式")){
            dispose();
            new LoginBoard();
        }
    }
}
