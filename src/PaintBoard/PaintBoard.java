package PaintBoard;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.*;

public class PaintBoard extends JFrame{

    public MainBoard mainBoard;
    ButtonGroup brushGroup;
    JToggleButton fpBtn;
    JToggleButton rectBtn;
    JToggleButton ovalBtn;
    JToggleButton lineBtn;
    JButton lineColorChooser;
    JButton bkColorChooser;
    JButton undoBtn;
    JButton clearBtn;
    JPanel ctrArea;
    JLabel curColor;
    JLabel curBkColor;
    JLabel curWeight;
    JTextField weightText;
    BasicArrowButton upButton;
    BasicArrowButton downButton;
    public PaintBoard(String title){
        super(title);
//        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        setBounds(300, 50, 1000, 700);
        addWindowListener(new MyWindowsListener());
        mainBoard = new MainBoard();
        mainBoard.setBorder(BorderFactory.createLoweredBevelBorder());
        lineColorChooser = new JButton();
        lineColorChooser.setBackground(mainBoard.getColor());
        lineColorChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color choosen = JColorChooser.showDialog(mainBoard,"nihao",null);
                if(choosen!=null){
                    mainBoard.setCurColor(choosen);
                    JButton b = (JButton) e.getSource();
                    b.setBackground(choosen);
                }

            }
        });
        bkColorChooser = new JButton();
        bkColorChooser.setBackground(mainBoard.getBkColor());
        bkColorChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color choosen = JColorChooser.showDialog(mainBoard,"nihao",null);
                if(choosen!=null){
                    mainBoard.setBkColor(choosen);
                    mainBoard.repaint();
                    JButton b = (JButton) e.getSource();
                    b.setBackground(choosen);
                }

            }
        });
        weightText = new JTextField(mainBoard.getWidth()+"");
        weightText.setPreferredSize(new Dimension(40,20));
        weightText.setText("5");
        weightText.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                JTextField tf = (JTextField) e.getSource();
                Integer num = Math.min(Math.max(Integer.parseInt(tf.getText())-e.getWheelRotation(),1),72);
                tf.setText(num.toString());
                mainBoard.setWeight(num);
            }
        });
        weightText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField tf = (JTextField) e.getSource();
                Integer num = Math.min(Math.max(Integer.parseInt(tf.getText()),1),72);
                tf.setText(num.toString());
                mainBoard.setWeight(Integer.parseInt(tf.getText()));
            }
        });
        upButton = new BasicArrowButton(BasicArrowButton.NORTH);
        downButton = new BasicArrowButton(BasicArrowButton.SOUTH);
        upButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField tf = weightText;
                Integer num = Integer.parseInt(weightText.getText());
                num = Math.min(Math.max(num+1,1),72);
                weightText.setText(num.toString());
                mainBoard.setWeight(num);
            }
        });
        downButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField tf = weightText;
                Integer num = Integer.parseInt(weightText.getText());
                num = Math.min(Math.max(num-1,1),72);
                weightText.setText(num.toString());
                mainBoard.setWeight(num);
            }
        });

        var p = getContentPane();
        p.setLayout(new BorderLayout());
        p.add(mainBoard,BorderLayout.CENTER);

        mainBoard.setMode("Free");
        brushGroup = new ButtonGroup();
        fpBtn = new JToggleButton("画笔");
        fpBtn.setSelected(true);
        fpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainBoard.setMode("Free");
            }
        });
        rectBtn = new JToggleButton("矩形");
        rectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainBoard.setMode("Rect");
            }
        });
        ovalBtn = new JToggleButton("椭圆");
        ovalBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainBoard.setMode("Oval");
            }
        });
        lineBtn = new JToggleButton("直线");
        lineBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainBoard.setMode("Line");
            }
        });
        brushGroup.add(fpBtn);
        brushGroup.add(rectBtn);
        brushGroup.add(ovalBtn);
        brushGroup.add(lineBtn);
        fpBtn.setBorder(BorderFactory.createEtchedBorder());
        rectBtn.setBorder(BorderFactory.createEtchedBorder());
        ovalBtn.setBorder(BorderFactory.createEtchedBorder());
        lineBtn.setBorder(BorderFactory.createEtchedBorder());
        undoBtn = new JButton("撤销");
        mainBoard.setUndoBtn(undoBtn);
        undoBtn.setEnabled(false);
        undoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainBoard.undo();
            }
        });
        clearBtn = new JButton("清屏");
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainBoard.clear();
            }
        });
        ctrArea = new JPanel();
        ctrArea.setLayout(new GridLayout());
        JPanel changeBrush = new JPanel();
        changeBrush.setLayout(new GridLayout(1,4,5,5));
//        changeBrush.add(brushGroup);
        changeBrush.add(fpBtn);
        changeBrush.add(rectBtn);
        changeBrush.add(ovalBtn);
        changeBrush.add(lineBtn);
        ctrArea.add(changeBrush);

        JPanel cmds = new JPanel();
        cmds.add(undoBtn);
        cmds.add(clearBtn);
        ctrArea.add(cmds);
        ctrArea.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        p.add(ctrArea,BorderLayout.NORTH);

        JPanel options = new JPanel();
        curColor = new JLabel("画笔颜色:");
        curBkColor = new JLabel("背景颜色:");
        curWeight = new JLabel("线条粗细");
        options.add(curColor);
        options.add(lineColorChooser);
        options.add(curBkColor);
        options.add(bkColorChooser);
        options.add(curWeight);
        options.add(weightText);
        JPanel upDownButton = new JPanel();
        upDownButton.setLayout(new GridLayout(2,1));
        upDownButton.add(upButton);
        upDownButton.add(downButton);
        options.add(upDownButton);
        curColor.setVisible(true);


        ctrArea.add(options);
        setVisible(true);
    }

    static class MyWindowsListener extends WindowAdapter{
        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

}
