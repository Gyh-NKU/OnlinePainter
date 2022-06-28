package Net;

import PaintBoard.PaintBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;

public class PaintPanel extends JPanel {
    public PaintBoard p;
    public Socket socket;
    public BufferedWriter writer;
    public BufferedReader reader;
    public ObjectInputStream objectInputStream;

    public PaintPanel(Socket socket) {
        try {
            this.socket = socket;
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            objectInputStream = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,"连接失败");
            return;
        }
        try {
            this.p = (PaintBoard) objectInputStream.readObject();
            System.out.println(this.p.paintArea.getDrawers().size());
            p.paintArea.repaint();
            p.reInit();
            System.out.println("读取成功");
        } catch (ClassNotFoundException|IOException e) {
            e.printStackTrace();
        }
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    writer.write("Exit");
                    writer.flush();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        p.setPreferredSize(new Dimension(800,600));
        add(p);
        PaintPanel.PointMouseListener listener = new PaintPanel.PointMouseListener();
        p.paintArea.addMouseListener(listener);
        p.paintArea.addMouseMotionListener(listener);
        new OpListener(reader, p).start();
        setButtons();
    }

    public void setButtons() {
        p.undoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    writer.write("Undo\n");
                    writer.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        p.clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    writer.write("Clear\n");
                    writer.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        p.bkColorChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Color choosen = JColorChooser.showDialog(p.paintArea, "选择颜色", null);
                    if (choosen != null) {
                        p.paintArea.setBkColor(choosen);
                        p.paintArea.repaint();
                        JButton b = (JButton) e.getSource();
                        b.setBackground(choosen);
                    }
                    writer.write("SetBK "+p.paintArea.getBkColor().getRGB()+"\n");
                    writer.flush();
                } catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    public class PointMouseListener extends MouseAdapter {

        public Point curPoint;

        @Override
        public void mousePressed(MouseEvent e) {
            curPoint = e.getPoint();
            try {
                System.out.println(p.paintArea.getMode());
                writer.write("sPoint " + curPoint.x + " " + curPoint.y + " " + p.paintArea.getMode()
                        + " " + p.paintArea.getCurColor().getRGB() + " " + p.paintArea.getWeight() + "\n");
                writer.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            curPoint = e.getPoint();
            try {
                writer.write("Point " + curPoint.x + " " + curPoint.y + " " + p.paintArea.getMode()
                        + " " + p.paintArea.getCurColor().getRGB() + " " + p.paintArea.getWeight() + "\n");
                writer.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            curPoint = e.getPoint();
            try {
                writer.write("ePoint " + curPoint.x + " " + curPoint.y + " " + p.paintArea.getMode()
                        + " " + p.paintArea.getCurColor().getRGB() + " " + p.paintArea.getWeight() + "\n");
                writer.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

//    public class OpListener extends Thread {
//        @Override
//        public void run() {
//            try {
//                for (; ; ) {
//                    String s = reader.readLine();
//                    Scanner sc = new Scanner(s);
//                    String op = sc.next();
//                    if ("sPoint".equals(op) || "Point".equals(op) || "ePoint".equals(op)) {
//                        int x = sc.nextInt();
//                        int y = sc.nextInt();
//                        Point point = new Point(x, y);
//                        PaintArea board = p.paintArea;
//                        String mode = sc.next();
//                        int colorRGB = sc.nextInt();
//                        int weight = sc.nextInt();
//                        Color color = new Color(colorRGB);
//                        if ("sPoint".equals(op)) {
//                            switch (mode) {
//                                case "Free":
//                                    board.getDrawers().add(new FreePainter(weight,
//                                            color));
//                                    var freePainter = (FreePainter) (board.getDrawers().
//                                            get(board.getDrawers().size() - 1));
//                                    var points = freePainter.points;
//                                    points.add(point);
//                                    break;
//                                case "Rect":
//                                    board.getDrawers().add(new RectPainter(point.x, point.y, point.x, point.y
//                                            , weight, color));
//                                    break;
//                                case "Oval":
//                                    board.getDrawers().add(new OvalPainter(point.x, point.y, point.x, point.y
//                                            , weight, color));
//                                    break;
//                                case "Line":
//                                    board.getDrawers().add(new LinePainter(point.x, point.y, point.x, point.y,
//                                            weight, color));
//                            }
//                        } else if ("Point".equals(op)) {
//                            switch (mode) {
//                                case "Free":
//                                    var freePainter = (FreePainter) board.getDrawers().
//                                            get(board.getDrawers().size() - 1);
//                                    var points = freePainter.points;
//                                    points.add(point);
//                                    break;
//                                case "Rect":
//                                    RectPainter p = (RectPainter) board.getDrawers()
//                                            .get(board.getDrawers().size() - 1);
//                                    p.setEnd(point);
//                                    break;
//                                case "Oval":
//                                    OvalPainter oop = (OvalPainter) board.getDrawers()
//                                            .get(board.getDrawers().size() - 1);
//                                    oop.setEnd(point);
//                                    break;
//                                case "Line":
//                                    LinePainter lp = (LinePainter) board.getDrawers()
//                                            .get(board.getDrawers().size() - 1);
//                                    lp.setEnd(point);
//                            }
//                        }
//                        board.repaint();
//                    }
//                    if ("Undo".equals(op)) {
//                        p.paintArea.undo();
//                    }
//                    if ("Clear".equals(op)) {
//                        p.paintArea.clear();
//                    }
//                }
//            } catch (IOException e) {
//
//            } finally {
//                System.out.println("接收信号线程结束！");
//            }
//
//        }
//    }
}


