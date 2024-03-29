package PaintBoard;

import java.awt.*;
import java.util.ArrayList;

public class FreePainter implements DrawImage {
    public ArrayList<Point> points;
    private int weight;
    private Color color;
//    GeneralPath path = new GeneralPath();


    public FreePainter(int weight, Color color) {
        this.weight = weight;
        this.color = color;
        points = new ArrayList<>();

    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public void draw(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        g.setColor(color);
        g.setBackground(Color.white);
        g.setStroke(new BasicStroke(weight));
        if (points.size() == 1) {
            Point p = points.get(0);
            g.fillOval(p.x - weight / 2, p.y - weight / 2, weight, weight);
//            path.moveTo(p.x, p.y);
        }
        for (int i = 1; i < points.size(); i++) {
            Point p1 = (Point) points.get(i - 1);
            Point p2 = (Point) points.get(i);
//            Point c1 = new Point((p1.x + p2.x)/2, p1.y);
//            Point c2 = new Point((p1.x + p2.x)/2, p2.y);
//            path.curveTo(c1.x, c1.y, c2.x, c2.y, p2.x, p2.y);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

    }
}
