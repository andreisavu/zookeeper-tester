package ro.pub.master.sii.zookeeper.core;

public class Node {

    private final String ip;


    public Node(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }
}
