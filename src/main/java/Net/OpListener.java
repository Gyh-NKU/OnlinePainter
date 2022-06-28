package Net;

import PaintBoard.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class OpListener extends Thread {
    BufferedReader reader;
    PaintBoard p;

    public OpListener(BufferedReader reader,PaintBoard p){
        this.reader = reader;
        this.p = p;
    }
    @Override
    public void run() {
        try {
            for (; ; ) {
                String s = reader.readLine();
                Scanner sc = new Scanner(s);
                String op = sc.next();
                if ("sPoint".equals(op) || "Point".equals(op) || "ePoint".equals(op)) {
                    int x = sc.nextInt();
                    int y = sc.nextInt();
                    Point point = new Point(x, y);
                    PaintArea board = p.paintArea;
                    String mode = sc.next();
                    int colorRGB = sc.nextInt();
                    int weight = sc.nextInt();
                    Color color = new Color(colorRGB);
                    if ("sPoint".equals(op)) {
                        switch (mode) {
                            case "Free":
                                board.getDrawers().add(new FreePainter(weight,
                                        color));
                                var freePainter = (FreePainter) (board.getDrawers().
                                        get(board.getDrawers().size() - 1));
                                var points = freePainter.points;
                                points.add(point);
                                break;
                            case "Rect":
                                board.getDrawers().add(new RectPainter(point.x, point.y, point.x, point.y
                                        , weight, color));
                                break;
                            case "Oval":
                                board.getDrawers().add(new OvalPainter(point.x, point.y, point.x, point.y
                                        , weight, color));
                                break;
                            case "Line":
                                board.getDrawers().add(new LinePainter(point.x, point.y, point.x, point.y,
                                        weight, color));
                        }
                    } else if ("Point".equals(op)) {
                        switch (mode) {
                            case "Free":
                                var freePainter = (FreePainter) board.getDrawers().
                                        get(board.getDrawers().size() - 1);
                                var points = freePainter.points;
                                points.add(point);
                                break;
                            case "Rect":
                                RectPainter p = (RectPainter) board.getDrawers()
                                        .get(board.getDrawers().size() - 1);
                                p.setEnd(point);
                                break;
                            case "Oval":
                                OvalPainter oop = (OvalPainter) board.getDrawers()
                                        .get(board.getDrawers().size() - 1);
                                oop.setEnd(point);
                                break;
                            case "Line":
                                LinePainter lp = (LinePainter) board.getDrawers()
                                        .get(board.getDrawers().size() - 1);
                                lp.setEnd(point);
                        }
                    }
                    board.repaint();
                }
                if ("Undo".equals(op)) {
                    p.paintArea.undo();
                }
                if ("Clear".equals(op)) {
                    p.paintArea.clear();
                }else if("SetBK".equals(op)){
                    int color = sc.nextInt();
                    System.out.println("changnk");
                    p.paintArea.setBkColor(new Color(color));
                }
            }
        } catch (IOException e) {

        } finally {
            System.out.println("接收信号线程结束！");
        }

    }
}
