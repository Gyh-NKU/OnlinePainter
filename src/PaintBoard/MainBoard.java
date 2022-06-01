package PaintBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.ArrayList;


public class MainBoard extends JPanel implements Serializable {

    private String mode;

    private Image imageBuffer;

    private ArrayList<DrawImage> drawers;

    private int weight;

    private Color curColor;

    private Color bkColor;

    private JButton undoBtn;

    private Eraser eraser;

    public void enableButton(JButton b) {
        b.setEnabled(true);
    }

    public void disableButton(JButton b) {
        b.setEnabled(false);
    }

    public void setCurColor(Color color) {
        this.curColor = color;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setUndoBtn(JButton b) {
        this.undoBtn = b;
    }

    public void setBkColor(Color bkColor) {
        this.bkColor = bkColor;
    }

    public void undo() {
        if (!drawers.isEmpty()) {
            drawers.remove(drawers.size() - 1);
        }
        if (drawers.isEmpty()) {
            disableButton(undoBtn);
        }
        repaint();
    }

    public void clear() {
        if (drawers.size() > 0) {
            drawers = new ArrayList<>();
        }
        disableButton(undoBtn);
        repaint();
    }

    public MainBoard() {
        weight = 5;
        curColor = Color.pink;
        drawers = new ArrayList<>();
        addMouseListener(new MyMouseListener());
        addMouseMotionListener(new MyMouseListener());
        bkColor = new Color(0xDDFFFF);
//        setBounds(0, 0, 300, 500);
//        setBorder(BorderFactory.createLineBorder(Color.red,10));
        setVisible(true);
        mode = "Free";
        undoBtn = null;
    }

    public MainBoard(String s){
        this();
        System.out.println(s);
    }

    @Override
    public void paint(Graphics g1) {
        super.paint(g1);
        if (!drawers.isEmpty() && undoBtn != null) {
            enableButton(undoBtn);
        }
        setBackground(bkColor);
        for (var i :
                drawers) {
            i.draw(g1);
        }
//        new PaintBoard.RectPainter(0, 0, 30, 30, 1, Color.black).draw(g1);
    }

    public Color getColor() {
        return curColor;
    }

    public Color getBkColor() {
        return bkColor;
    }

    private static class MyMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            MainBoard board = (MainBoard) e.getSource();
            String mode = board.mode;
            switch (mode) {
                case "Free":
                    board.drawers.add(new FreePainter(board.weight,
                            board.curColor));
                    var freePainter = (FreePainter) (board.drawers.
                            get(board.drawers.size() - 1));
                    var points = freePainter.points;
                    points.add(e.getPoint());
                    break;
                case "Rect":
                    board.drawers.add(new RectPainter(e.getX(), e.getY(), e.getX(), e.getY()
                            , board.weight, board.curColor));
                    break;
                case "Oval":
                    board.drawers.add(new OvalPainter(e.getX(), e.getY(), e.getX(), e.getY()
                            , board.weight, board.curColor));
                    break;
                case "Line":
                    board.drawers.add(new LinePainter(e.getX(), e.getY(), e.getX(), e.getY(),
                            board.weight, board.curColor));
            }
            board.repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            MainBoard board = (MainBoard) e.getSource();
            String mode = board.mode;
            switch (mode) {
                case "Free":
                    var freePainter = (FreePainter) board.drawers.
                            get(board.drawers.size() - 1);
                    var points = freePainter.points;
                    points.add(e.getPoint());
                    break;
                case "Rect":
                    RectPainter p = (RectPainter) board.drawers.get(board.drawers.size() - 1);
                    p.setEnd(e.getPoint());
                    break;
                case "Oval":
                    OvalPainter op = (OvalPainter) board.drawers.get(board.drawers.size() - 1);
                    op.setEnd(e.getPoint());
                    break;
                case "Line":
                    LinePainter lp = (LinePainter) board.drawers.get(board.drawers.size() - 1);
                    lp.setEnd(e.getPoint());
            }
            board.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
        }
    }

    class Eraser implements DrawImage {
        @Override
        public void draw(Graphics g) {

        }
    }
}