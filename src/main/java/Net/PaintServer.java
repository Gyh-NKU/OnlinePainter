package Net;

import PaintBoard.*;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class PaintServer {
    ArrayList<Socket> sockets = new ArrayList<>();

    private int port;

    private PaintBoard p = new PaintBoard();

    public PaintServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket ss = new ServerSocket(port);
        p = new PaintBoard();
        System.out.println("画图服务器开启...");
        for (; ; ) {
            Socket sock = ss.accept();
            sockets.add(sock);
            System.out.println("获得连接，ip地址： " + (sock).getRemoteSocketAddress());
            System.out.println("当前连接数： " + sockets.size());
            new PaintHandler(sock, sockets, p).start();
        }
    }
}

//class MyOpListener extends Thread {
//    @Override
//    public void run() {
//        try {
//
//        } catch (IOException e) {
//
//        } finally {
//            System.out.println("接收信号线程结束！");
//        }
//    }
//}

class PaintHandler extends Thread {
    Socket socket;

    ArrayList<Socket> allSockets;

    PaintBoard p;

    public PaintHandler(Socket socket, ArrayList<Socket> sockets, PaintBoard p) {
        this.socket = socket;
        this.allSockets = sockets;
        this.p = p;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(p);
            objectOutputStream.flush();
            for (; ; ) {

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String s = reader.readLine();

                if ("Exit".equals(s)) { // 退出指令断开连接
                    this.socket.close();
                    allSockets.remove(socket);
                    break;
                } else {
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
                    }
                    if("SetBK".equals(op)){
                        int color = sc.nextInt();
                        p.bkColorChooser.setBackground(new Color(color));
                        p.paintArea.setBkColor(new Color(color));
                    }

                    //转发
                    for (var j : allSockets) {
                        BufferedWriter writer1 = new BufferedWriter(new OutputStreamWriter(j.getOutputStream()));
                        writer1.write(s + "\n");
                        writer1.flush();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println(socket + " 断开连接！");
            System.out.println("当前连接数： " + allSockets.size());
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

