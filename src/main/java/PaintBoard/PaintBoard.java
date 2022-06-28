package PaintBoard;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;

public class PaintBoard extends JPanel {

    public PaintArea paintArea;
    public ButtonGroup brushGroup;
    public JToggleButton fpBtn;
    public JToggleButton rectBtn;
    public JToggleButton ovalBtn;
    public JToggleButton lineBtn;
    public JButton lineColorChooser;
    public JButton bkColorChooser;
    public JButton undoBtn;
    public JButton clearBtn;
    public JPanel ctrArea;
    public JLabel curColor;
    public JLabel curBkColor;
    public JLabel curWeight;
    public JTextField weightText;
    public BasicArrowButton upButton;
    public BasicArrowButton downButton;
    public BasicArrowButton showHideChat;

    public PaintBoard() {
//        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//        setBounds(300, 50, 1000, 700);
//        addWindowListener(new MyWindowsListener());
        paintArea = new PaintArea();
        paintArea.setBorder(BorderFactory.createLoweredBevelBorder());
        lineColorChooser = new JButton();
        lineColorChooser.setBackground(paintArea.getColor());
        lineColorChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color choosen = JColorChooser.showDialog(paintArea, "选择颜色", null);
                if (choosen != null) {
                    paintArea.setCurColor(choosen);
                    JButton b = (JButton) e.getSource();
                    b.setBackground(choosen);
                }

            }
        });
        bkColorChooser = new JButton();
        bkColorChooser.setBackground(paintArea.getBkColor());
        bkColorChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color choosen = JColorChooser.showDialog(paintArea, "选择颜色", null);
                if (choosen != null) {
                    paintArea.setBkColor(choosen);
                    paintArea.repaint();
                    JButton b = (JButton) e.getSource();
                    b.setBackground(choosen);
                }

            }
        });
        weightText = new JTextField(paintArea.getWidth() + "");
        weightText.setPreferredSize(new Dimension(40, 20));
        weightText.setText("5");
        weightText.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                JTextField tf = (JTextField) e.getSource();
                Integer num = Math.min(Math.max(Integer.parseInt(tf.getText()) - e.getWheelRotation(), 1), 72);
                tf.setText(num.toString());
                paintArea.setWeight(num);
            }
        });
        weightText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField tf = (JTextField) e.getSource();
                Integer num = Math.min(Math.max(Integer.parseInt(tf.getText()), 1), 72);
                tf.setText(num.toString());
                paintArea.setWeight(Integer.parseInt(tf.getText()));
            }
        });
        upButton = new BasicArrowButton(BasicArrowButton.NORTH);
        downButton = new BasicArrowButton(BasicArrowButton.SOUTH);
        upButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField tf = weightText;
                Integer num = Integer.parseInt(weightText.getText());
                num = Math.min(Math.max(num + 1, 1), 72);
                weightText.setText(num.toString());
                paintArea.setWeight(num);
            }
        });
        downButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField tf = weightText;
                Integer num = Integer.parseInt(weightText.getText());
                num = Math.min(Math.max(num - 1, 1), 72);
                weightText.setText(num.toString());
                paintArea.setWeight(num);
            }
        });

        setLayout(new BorderLayout());
        add(paintArea, BorderLayout.CENTER);

        paintArea.setMode("Free");
        brushGroup = new ButtonGroup();
        fpBtn = new JToggleButton("画笔");
        fpBtn.setSelected(true);
        fpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintArea.setMode("Free");
            }
        });
        rectBtn = new JToggleButton("矩形");
        rectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintArea.setMode("Rect");
            }
        });
        ovalBtn = new JToggleButton("椭圆");
        ovalBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintArea.setMode("Oval");
            }
        });
        lineBtn = new JToggleButton("直线");
        lineBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintArea.setMode("Line");
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
        paintArea.setUndoBtn(undoBtn);
        undoBtn.setEnabled(false);
        undoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintArea.undo();
            }
        });
        clearBtn = new JButton("清屏");
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintArea.clear();
            }
        });
        ctrArea = new JPanel();
        ctrArea.setLayout(new BorderLayout());
        JPanel changeBrush = new JPanel();
        changeBrush.setLayout(new GridLayout(1, 4, 5, 5));
//        changeBrush.add(brushGroup);
        changeBrush.add(fpBtn);
        changeBrush.add(rectBtn);
        changeBrush.add(ovalBtn);
        changeBrush.add(lineBtn);
        ctrArea.add(changeBrush, BorderLayout.WEST);

        JPanel cmds = new JPanel();
        cmds.add(undoBtn);
        cmds.add(clearBtn);
//        ctrArea.add(cmds);
        ctrArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        add(ctrArea, BorderLayout.NORTH);

        JPanel options = new JPanel();
        options.add(cmds);
        curColor = new JLabel("画笔颜色：");
        curBkColor = new JLabel("背景颜色：");
        curWeight = new JLabel("线条粗细：");
        options.add(curColor);
        options.add(lineColorChooser);
        options.add(curBkColor);
        options.add(bkColorChooser);
        options.add(curWeight);
        options.add(weightText);
        JPanel upDownButton = new JPanel();
        upDownButton.setLayout(new GridLayout(2, 1));
        upDownButton.add(upButton);
        upDownButton.add(downButton);
        options.add(upDownButton);
        showHideChat = new BasicArrowButton(SwingConstants.WEST);

        curColor.setVisible(true);

        ctrArea.add(options);
        ctrArea.add(showHideChat, BorderLayout.EAST);
//        setVisible(true);
    }

    public void reInit(){
        lineColorChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color choosen = JColorChooser.showDialog(paintArea, "选择颜色", null);
                if (choosen != null) {
                    paintArea.setCurColor(choosen);
                    JButton b = (JButton) e.getSource();
                    b.setBackground(choosen);
                }

            }
        });
        bkColorChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                Color choosen = JColorChooser.showDialog(paintArea, "选择颜色", null);
//                if (choosen != null) {
//                    paintArea.setBkColor(choosen);
//                    paintArea.repaint();
//                    JButton b = (JButton) e.getSource();
//                    b.setBackground(choosen);
//                }
            }
        });
        weightText.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                JTextField tf = (JTextField) e.getSource();
                Integer num = Math.min(Math.max(Integer.parseInt(tf.getText()) - e.getWheelRotation(), 1), 72);
                tf.setText(num.toString());
                paintArea.setWeight(num);
            }
        });
        weightText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField tf = (JTextField) e.getSource();
                Integer num = Math.min(Math.max(Integer.parseInt(tf.getText()), 1), 72);
                tf.setText(num.toString());
                paintArea.setWeight(Integer.parseInt(tf.getText()));
            }
        });
        upButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField tf = weightText;
                Integer num = Integer.parseInt(weightText.getText());
                num = Math.min(Math.max(num + 1, 1), 72);
                weightText.setText(num.toString());
                paintArea.setWeight(num);
            }
        });
        downButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField tf = weightText;
                Integer num = Integer.parseInt(weightText.getText());
                num = Math.min(Math.max(num - 1, 1), 72);
                weightText.setText(num.toString());
                paintArea.setWeight(num);
            }
        });
        fpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintArea.setMode("Free");
            }
        });
        rectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintArea.setMode("Rect");
            }
        });
        ovalBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintArea.setMode("Oval");
            }
        });
        lineBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintArea.setMode("Line");
            }
        });
//        undoBtn.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                paintArea.undo();
//            }
//        });
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintArea.clear();
            }
        });
    }

//    static class MyWindowsListener extends WindowAdapter{
//        @Override
//        public void windowClosing(WindowEvent e) {
//            System.exit(0);
//        }
//    }

}
