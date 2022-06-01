package Net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    static ArrayList<Socket> sockets = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(6666);
        System.out.println("server is running...");
        for (; ; ) {
            Socket sock = ss.accept();
            sockets.add(sock);
            System.out.println("connected from " + ((Socket) sock).getRemoteSocketAddress());
            new Handler(sock, sockets).start();
        }
    }
}

class Handler extends Thread{
    Socket socket;

    ArrayList<Socket> allSockets;

    public Handler(Socket socket, ArrayList<Socket> sockets) {
        this.socket = socket;
        this.allSockets = sockets;
    }

    @Override
    public void run() {
        try {
            for (;;) {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream oi = new ObjectInputStream(socket.getInputStream());
                Object o = oi.readObject();

                for(var j:allSockets){
                    if(!j.equals(socket)){
                        System.out.println(socket.toString()+"   "+o);
                        ObjectOutputStream oo = new ObjectOutputStream(j.getOutputStream());
                        oo.writeObject(o);
                        oo.flush();
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

    }
}

