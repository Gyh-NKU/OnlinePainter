package PaintBoard;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyMouseListener extends MouseAdapter {
    @Override
    public void mousePressed(MouseEvent e) {
        PaintArea board = (PaintArea) e.getSource();
        String mode = board.getMode();
        switch (mode) {
            case "Free":
                board.getDrawers().add(new FreePainter(board.getWeight(),
                        board.getCurColor()));
                var freePainter = (FreePainter) (board.getDrawers().
                        get(board.getDrawers().size() - 1));
                var points = freePainter.points;
                points.add(e.getPoint());
                break;
            case "Rect":
                board.getDrawers().add(new RectPainter(e.getX(), e.getY(), e.getX(), e.getY()
                        , board.getWeight(), board.getCurColor()));
                break;
            case "Oval":
                board.getDrawers().add(new OvalPainter(e.getX(), e.getY(), e.getX(), e.getY()
                        , board.getWeight(), board.getCurColor()));
                break;
            case "Line":
                board.getDrawers().add(new LinePainter(e.getX(), e.getY(), e.getX(), e.getY(),
                        board.getWeight(), board.getCurColor()));
        }
        board.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        PaintArea board = (PaintArea) e.getSource();
        String mode = board.getMode();
        switch (mode) {
            case "Free":
                var freePainter = (FreePainter) board.getDrawers().
                        get(board.getDrawers().size() - 1);
                var points = freePainter.points;
                points.add(e.getPoint());
                break;
            case "Rect":
                RectPainter p = (RectPainter) board.getDrawers().get(board.getDrawers().size() - 1);
                p.setEnd(e.getPoint());
                break;
            case "Oval":
                OvalPainter op = (OvalPainter) board.getDrawers().get(board.getDrawers().size() - 1);
                op.setEnd(e.getPoint());
                break;
            case "Line":
                LinePainter lp = (LinePainter) board.getDrawers().get(board.getDrawers().size() - 1);
                lp.setEnd(e.getPoint());
        }
        board.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
    }
}
