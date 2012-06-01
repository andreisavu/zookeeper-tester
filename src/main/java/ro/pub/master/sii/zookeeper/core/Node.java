package ro.pub.master.sii.zookeeper.core;

public class Node {

    private final String ip;


    public Node(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public String getUri() {
        return "/nodes/" + ip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (ip != null ? !ip.equals(node.ip) : node.ip != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return ip != null ? ip.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Node{" +
            "ip='" + ip + '\'' +
            '}';
    }
}
