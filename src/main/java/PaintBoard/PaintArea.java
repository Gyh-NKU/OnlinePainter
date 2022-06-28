package PaintBoard;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;


public class PaintArea extends JPanel implements Serializable {

    private String mode;

    private CopyOnWriteArrayList<DrawImage> drawers;

    private int weight;

    private Color curColor;

    private Color bkColor;

    private JButton undoBtn;

    private Eraser eraser;

    private Point pressedPoint;

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
        repaint();
    }

    public void setPressedPoint(Point pressedPoint) {
        this.pressedPoint = pressedPoint;
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
            drawers = new CopyOnWriteArrayList<DrawImage>();
        }
        disableButton(undoBtn);
        repaint();
    }

    public PaintArea() {
        weight = 5;
        curColor = Color.pink;
        drawers = new CopyOnWriteArrayList<DrawImage>();
        addMouseListener(new MyMouseListener());
        addMouseMotionListener(new MyMouseListener());
        bkColor = new Color(0xDDFFFF);
//        setBounds(0, 0, 300, 500);
//        setBorder(BorderFactory.createLineBorder(Color.red,10));
        setVisible(true);
        mode = "Free";
        undoBtn = null;
    }

    public PaintArea(String s) {
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

    public String getMode() {
        return mode;
    }

    public Point getPressedPoint() {
        return pressedPoint;
    }

    public CopyOnWriteArrayList<DrawImage> getDrawers() {
        return drawers;
    }

    public int getWeight() {
        return weight;
    }

    public Color getCurColor() {
        return curColor;
    }

    public JButton getUndoBtn() {
        return undoBtn;
    }

    public Eraser getEraser() {
        return eraser;
    }

    class Eraser implements DrawImage {
        @Override
        public void draw(Graphics g) {

        }
    }
}