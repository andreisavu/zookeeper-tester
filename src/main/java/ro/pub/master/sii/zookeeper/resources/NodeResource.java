package ro.pub.master.sii.zookeeper.resources;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.apache.commons.ssl.asn1.Strings;
import org.apache.whirr.Cluster;
import org.apache.whirr.ClusterController;
import org.apache.whirr.ClusterSpec;
import org.apache.whirr.state.ClusterStateStore;
import org.apache.whirr.state.ClusterStateStoreFactory;
import ro.pub.master.sii.zookeeper.config.TesterConfiguration;
import ro.pub.master.sii.zookeeper.core.Node;

@Path("/nodes")
@Produces(MediaType.APPLICATION_JSON)
public class NodeResource {

    private TesterConfiguration config;
    private Cluster cluster;

    public NodeResource(TesterConfiguration config) throws Exception {
        this.config = config;
        this.cluster = loadCluster(config.getClusterSpec());
    }

    private Cluster loadCluster(ClusterSpec spec) throws Exception {
        ClusterController controller = new ClusterController();
        ClusterStateStore stateStore = new ClusterStateStoreFactory().create(spec);

        return new Cluster(controller.getInstances(spec, stateStore));
    }

    @GET
    public Set<Node> list() {
        return Sets.newHashSet(Iterables.transform(cluster.getInstances(),
            new Function<Cluster.Instance, Node>() {
                public Node apply(Cluster.Instance instance) {
                    return new Node(instance.getPublicIp());
                }
            }));
    }

    @GET
    @Path("{ip}")
    public Map<String, String> getInfo(@PathParam("ip") String ip) throws IOException {
        Map<String, String> result = Maps.newHashMap();
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(ip, 2181));
            socket.getOutputStream().write(("mntr\n").getBytes());

            for (String line : CharStreams.readLines(
                new InputStreamReader(socket.getInputStream()))) {
                List<String> parts = Lists.newArrayList(Splitter.on("\t").split(line));
                result.put(parts.get(0), parts.get(1));
            }
            return result;

        } finally {
            if (socket.isConnected()) {
                socket.close();
            }
        }
    }
}
