package Net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatServer {
    public static CopyOnWriteArrayList<Socket> sockets = new CopyOnWriteArrayList<Socket>();

    int port;

    public ChatServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket ss = new ServerSocket(port);
        System.out.println("聊天服务器开启...");
        for (; ; ) {
            Socket sock = ss.accept();
            sockets.add(sock);
            System.out.println("获得连接，ip地址： " + ((Socket) sock).getRemoteSocketAddress());
            new ChatHandler(sock, sockets).start();
        }
    }
}

class ChatHandler extends Thread {
    Socket socket;
    String userName;
    CopyOnWriteArrayList<Socket> allSockets;


    public ChatHandler(Socket socket, CopyOnWriteArrayList<Socket> allSockets) {
        this.socket = socket;
        this.allSockets = allSockets;
    }

    void callAll(String s) throws IOException {
        for (var j : allSockets) {
            Writer w = new BufferedWriter(new OutputStreamWriter(j.getOutputStream(), StandardCharsets.UTF_8));
            w.write(s);
            w.flush();
        }
    }

    void callI(int i, String s) throws IOException {
        var j = allSockets.get(i);
        System.out.println("calli" + i);
        Writer w = new BufferedWriter(new OutputStreamWriter(j.getOutputStream(), StandardCharsets.UTF_8));
        w.write(s);
        w.flush();
    }

    void callWithoutI(int i, String s) throws IOException {
        for (int k = 0; k < allSockets.size(); k++) {
            if (k != i) {
                System.out.println("callwi" + k);
                var j = allSockets.get(k);
                Writer w = new BufferedWriter(new OutputStreamWriter(j.getOutputStream(), StandardCharsets.UTF_8));
                w.write(s);
                w.flush();
            }

        }
    }

    @Override
    public void run() {
        try (InputStream input = socket.getInputStream()) {
            var reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
            var writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            userName = reader.readLine();
            callAll("[server]\n" + userName + " 进入了房间。\n");
            writer.flush();
            for (; ; ) {
                String s = reader.readLine();
                if (s.equals("bye")) {
                    callAll("[server]\n" + userName + " 离开了房间。\n");
                    writer.write("bye\n");
                    writer.flush();
                    ChatServer.sockets.remove(socket);
                    break;
                }
                callAll("[" + userName + "]" + "\n" + s + "\n");
            }
        } catch (IOException e) {
            try {
                this.socket.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
