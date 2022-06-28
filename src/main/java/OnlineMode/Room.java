package OnlineMode;

import Net.GuessServer;

public class Room {
    GuessServer server;
    public static final int Maxplayer=6;
    public static final int GAMING=1;
    public static final int WAITING=0;
    int playerNum =0;
    int watcherNum =0;
    String status;
    int statusnum;
    String name;
    public Room(String name) {
        this.name = name;
        status="等待游戏";
        statusnum=WAITING;
    }
    public int getPlayerNum() {
        return playerNum;
    }
    public int getWatcherNum() {
        return watcherNum;
    }
    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }
    public void setWatcherNum(int watcherNum) {
        this.watcherNum = watcherNum;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setStatusnum(int statusnum) {
        this.statusnum = statusnum;
    }
    public int getStatusnum() {
        return statusnum;
    }
}
