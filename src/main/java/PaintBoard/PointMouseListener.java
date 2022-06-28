package PaintBoard;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PointMouseListener extends MouseAdapter {

    public Point curPoint;

    @Override
    public void mousePressed(MouseEvent e) {
        curPoint = e.getPoint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        curPoint = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        curPoint = e.getPoint();
    }
}
