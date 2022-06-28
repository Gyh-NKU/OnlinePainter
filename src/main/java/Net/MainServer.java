package Net;

import java.io.IOException;

public class MainServer {
    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                try {
                    new ChatServer(6667).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                try {
                    new PaintServer(6666).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread(()->{
            try {
                new GuessServer(6668).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
