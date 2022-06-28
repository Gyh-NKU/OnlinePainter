package Net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class GuessServer extends ChatServer {

    CopyOnWriteArrayList<String> allUserName;


    public GuessServer(int port) {
        super(port);
    }

    @Override
    public void start() throws IOException {
        ServerSocket ss = new ServerSocket(port);
        System.out.println("你画我猜服务器开启...");
        allUserName = new CopyOnWriteArrayList<>();

        for (; ; ) {
            Socket sock = ss.accept();
            sockets.add(sock);
            System.out.println("获得连接，ip地址： " + ((Socket) sock).getRemoteSocketAddress());
            new GuessHandler(sock, sockets, allUserName).start();
        }
    }

    public static void main(String[] args) {
        try {
            new GuessServer(6668).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class GuessHandler extends ChatHandler {
    final CopyOnWriteArrayList<String> allUserName;
    static CopyOnWriteArrayList<String> preparedUser = new CopyOnWriteArrayList<>();
    static ArrayList<String> list = new ArrayList<String>();
    static String word;
    static String[] words;
    static {
        list.add("乌龟#一种爬行动物，两个字");
        list.add("梨子#一种水果，两个字");
        list.add("胡萝卜#一种蔬菜，三个字");
        list.add("菜鸟#一种动物，两个字");
        list.add("大树#一种植物，两个字");
        list.add("猪#一种动物，一个字");
        list.add("葡萄#一种水果，两个字");
        list.add("辣条#一种食品，两个字");
        list.add("苹果#一种水果，两个字");
        list.add("蜜蜂#一种动物，两个字");
        list.add("程序员#一种职业，三个字");
        list.add("笔记本电脑#一种电子设备，五个字");
        list.add("鼠标#配件，两个字");
        list.add("三只松鼠#零食品牌，四个字");
        list.add("马拉松#一项运动名称，三个字");
        list.add("跳远#一项运动，两个字");
        list.add("兔子#一种动物，两个字");
        list.add("凤凰#一种鸟类，两个字");
    }
    public GuessHandler(Socket socket, CopyOnWriteArrayList<Socket> allSockets, CopyOnWriteArrayList<String> allUserName) {
        super(socket, allSockets);
        this.allUserName = allUserName;
    }




    @Override
    public void run() {
        try (InputStream input = socket.getInputStream()) {
            var reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
            var writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            userName = reader.readLine();
            allUserName.add(userName);
            callAll("[server]\n" + userName + " 进入了房间。\n");
            writer.flush();
            for (; ; ) {
                String s = reader.readLine();
                if (preparedUser.size() == allUserName.size()){
                    if(Objects.equals(s, words[0])){
                        callAll("[server]\n"+userName+" 最先猜出！\n");
                        callAll("[server]\n"+"答案是"+words[0]+"\n");
                        synchronized (allUserName){
                            preparedUser = new CopyOnWriteArrayList<>();
                        }
                    }else {
                        callAll("["+userName+"]\n"+s+"\n");
                    }
                    System.out.println("真好！");
                    continue;
                }
                if (s.equals("bye")) {
                    callAll("[server]\n" + userName + " 离开了房间。\n");
                    writer.write("bye\n");
                    writer.flush();
                    ChatServer.sockets.remove(socket);
                    allUserName.remove(userName);
                    break;
                } else if (s.equals("准备")) {
                    callAll("[server]\n" + userName + " 已经准备。");
                    preparedUser.add(userName);
                    callAll("("+preparedUser.size()+"/"+allUserName.size()+")\n");
                    if (preparedUser.size() == allUserName.size()) {
                        callAll("[server]\n" + "游戏开始！\n");
                        Random rd = new Random();
                        int painter = rd.nextInt(allUserName.size());
                        word = list.get(rd.nextInt(list.size()));
                        words = word.split("#");
                        callI(painter, "[server]\n" + "你被选为画图者！\n");
                        callI(painter,"[server]\n"+"你要画的词语是： "+words[0]+"\n");
                        callWithoutI(painter, "[server]\n" + "你被选择为猜的人！\n");
                        callWithoutI(painter,"[server]\n"+"提示： "+words[1]+"\n");
                    }
                } else if (s.equals("取消准备")) {
                    callAll("[server]\n" + userName + " 取消了准备。");
                    preparedUser.remove(userName);
                    callAll("("+preparedUser.size()+"/"+allUserName.size()+")\n");
                } else {
                    System.out.println(s);
                    callAll("[" + userName + "]" + "\n" + s + "\n");
                }

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


