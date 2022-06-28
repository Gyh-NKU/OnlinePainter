package OnlineMode;

import Style.VerticalFlowLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class RoomSelector extends JFrame implements ActionListener {
    JPanel upperPanel;
    JButton addRoom;
    JButton exitButton;
    JPanel roomPanel;

    public RoomSelector(){
        super("选择房间");
        setBounds(200, 200, 650, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        upperPanel = new JPanel(new GridLayout(1,6,0,20));

        String[] names = { "房间名", "玩家人数", "观战人数", "状态"};
        for (int i = 0; i < 4; i++) {
            JLabel jLabel = new JLabel(names[i]);
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            upperPanel.add(jLabel);
        }
        addRoom = new JButton("创建房间");
        addRoom.addActionListener(this);
        upperPanel.add(addRoom);
        exitButton = new JButton("返回");
        upperPanel.add(exitButton);
        upperPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        add(upperPanel,BorderLayout.NORTH);

        roomPanel = new JPanel();
        roomPanel.setLayout(new VerticalFlowLayout());
//        roomPanel.add(new RoomPanel("fj"));
//        roomPanel.add(new RoomPanel("fj1"));
//        roomPanel.add(new RoomPanel("fj2"));
        add(roomPanel,BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        new RoomSelector();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if(Objects.equals(button.getText(), "创建房间")){
            String name = JOptionPane.showInputDialog(this,"输入房间名");
            System.out.println(name);
            if(name!=null){
                roomPanel.add(new RoomPanel(name));
                roomPanel.revalidate();
            }
        }
    }
}