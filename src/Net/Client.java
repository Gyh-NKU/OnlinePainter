package Net;

import PaintBoard.MainBoard;
import PaintBoard.PaintBoard;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Client {
    public static PaintBoard p = new PaintBoard("画图");

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket(InetAddress.getLocalHost(), 6666);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(p.mainBoard);
        oos.flush();

        ScheduledExecutorService u = Executors.newScheduledThreadPool(1);
        u.scheduleAtFixedRate(new Thread(() -> {
            System.out.println("upload");
            try {
                ObjectOutputStream oos2 = new ObjectOutputStream(socket.getOutputStream());
                oos2.writeObject(p.mainBoard);
                oos2.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }), 100, 10000, TimeUnit.MILLISECONDS);

        ScheduledExecutorService d = Executors.newScheduledThreadPool(1);

        d.scheduleAtFixedRate(new Thread(()->{
            System.out.println("download");
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                assert ois != null;
                p.mainBoard = (MainBoard) ois.readObject();
                p.mainBoard.repaint();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }),100,10000,TimeUnit.MILLISECONDS);


    }
}


