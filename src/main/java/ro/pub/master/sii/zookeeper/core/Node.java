package ro.pub.master.sii.zookeeper.core;

import org.apache.whirr.Cluster;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

public class Node {

    private final String id;
    private final String publicIp;
    private final String hostname;

    public Node(Cluster.Instance instance) throws IOException {
        this(
            instance.getId(),
            instance.getPublicIp(),
            instance.getPublicHostName());
    }

    public Node(
        @JsonProperty("id") String id,
        @JsonProperty("publicIp") String publicIp,
        @JsonProperty("hostname") String hostname
    ) {
        this.id = checkNotNull(id, "id");
        this.publicIp = checkNotNull(publicIp, "ip");
        this.hostname = checkNotNull(hostname, "hostname");
    }

    public String getId() {
        return id;
    }

    public String getPublicIp() {
        return publicIp;
    }

    public String getHostname() {
        return hostname;
    }

    public String getMonitoringData() {
        return "/nodes/" + publicIp + "/mntr";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (hostname != null ? !hostname.equals(node.hostname) : node.hostname != null) return false;
        if (id != null ? !id.equals(node.id) : node.id != null) return false;
        if (publicIp != null ? !publicIp.equals(node.publicIp) : node.publicIp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (publicIp != null ? publicIp.hashCode() : 0);
        result = 31 * result + (hostname != null ? hostname.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Node{" +
            "id='" + id + '\'' +
            ", publicIp='" + publicIp + '\'' +
            ", hostname='" + hostname + '\'' +
            '}';
    }
}
