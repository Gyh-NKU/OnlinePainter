import java.awt.*;

public class RectPainter implements DrawImage {
    int x1, y1, x2, y2;
    int weight;
    Color color;
    boolean dashed;

    public void setEnd(Point p){
        x2 = p.x;
        y2 = p.y;
    }

    public RectPainter(int x1, int y1, int x2, int y2, int weight, Color color) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.weight = weight;
        this.color = color;
    }

    public RectPainter(int x1, int y1, int x2, int y2, int weight, Color color, boolean dashed) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.weight = weight;
        this.color = color;
        this.dashed = dashed;
    }

    @Override
    public void draw(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        if (dashed)
            g.setStroke(new BasicStroke(weight, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER, 10.0f, new float[]{3.0f, 3.0f}, 3.0f));
        else
            g.setStroke(new BasicStroke(weight));
        g.setColor(color);
        if (x2 > x1 && y2 > y1) {
            g.drawRect(x1, y1, x2 - x1, y2 - y1);
        } else if (x2 > x1 && y2 < y1) {
            g.drawRect(x1, y2, x2 - x1, y1 - y2);
        } else if (x2 < x1 && y2 > y1) {
            g.drawRect(x2, y1, x1 - x2, y2 - y1);
        } else if (x2 < x1 && y2 < y1) {
            g.drawRect(x2, y2, x1 - x2, y1 - y2);
        }

    }
}
