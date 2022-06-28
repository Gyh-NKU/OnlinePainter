package PaintBoard;

import java.awt.*;

public class LinePainter implements DrawImage {
    int x1, x2, y1, y2;
    private int weight;
    private Color color;

    public void setEnd(Point p) {
        x2 = p.x;
        y2 = p.y;
    }

    public LinePainter(int x1, int y1, int x2, int y2, int weight, Color color) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.weight = weight;
        this.color = color;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void draw(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        g.setColor(color);
        g.setStroke(new BasicStroke(weight));
        g.drawLine(x1, y1, x2, y2);
    }
}
