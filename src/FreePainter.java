import java.awt.*;
import java.util.ArrayList;

public class FreePainter implements DrawImage{
    public ArrayList<Point> points;
    private int weight;
    private Color color;


    public FreePainter(int weight, Color color) {
        this.weight = weight;
        this.color = color;
        points = new ArrayList<>();
    }

    public void setColor(Color color){
        this.color = color;
    }
    public void setWeight(int weight){
        this.weight = weight;
    }
    @Override
    public void draw(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        g.setColor(color);
        g.setBackground(Color.white);
        g.setStroke(new BasicStroke(weight));
        if(points.size() == 1){
            Point p = points.get(0);
            g.fillOval(p.x-weight/2,p.y-weight/2,weight,weight);
        }
        for (int i = 1; i < points.size(); i++) {
            Point p1 = (Point) points.get(i - 1);
            Point p2 = (Point) points.get(i);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }
}
