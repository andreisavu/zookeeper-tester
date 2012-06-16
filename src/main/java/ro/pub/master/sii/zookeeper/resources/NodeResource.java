package ro.pub.master.sii.zookeeper.resources;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.base.Throwables;
import com.google.common.collect.*;
import com.google.common.io.CharStreams;
import com.yammer.dropwizard.logging.Log;
import org.apache.whirr.Cluster;
import org.apache.whirr.ClusterController;
import org.apache.whirr.ClusterSpec;
import org.apache.whirr.state.ClusterStateStore;
import org.apache.whirr.state.ClusterStateStoreFactory;
import ro.pub.master.sii.zookeeper.config.TesterConfiguration;
import ro.pub.master.sii.zookeeper.core.Node;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Path("/nodes")
@Produces(MediaType.APPLICATION_JSON)
public class NodeResource {

    private static final Log LOG = Log.forClass(NodeResource.class);

    public static final int DEFAULT_PORT = 2181;
    private Cluster cluster;

    public NodeResource(TesterConfiguration config) throws Exception {
        this.cluster = loadCluster(config.getClusterSpec());
    }

    private Cluster loadCluster(ClusterSpec spec) throws Exception {
        ClusterController controller = new ClusterController();
        ClusterStateStore stateStore = new ClusterStateStoreFactory().create(spec);

        Set<Cluster.Instance> instances = controller.getInstances(spec, stateStore);
        for (Cluster.Instance machine : instances) {
            LOG.info(machine.toString());
        }
        return new Cluster(instances);
    }

    @GET
    public Set<Node> list() {
        return Sets.newHashSet(Iterables.transform(cluster.getInstances(),
            new Function<Cluster.Instance, Node>() {
                public Node apply(Cluster.Instance instance) {
                    try {
                        return new Node(instance);
                    } catch (IOException e) {
                        LOG.error(e, "Failed creating node object");
                        throw Throwables.propagate(e);
                    }
                }
            }));
    }

    @GET
    @Path("{ip}/mntr")
    public Map<String, String> getInfo(@PathParam("ip") String ip) throws IOException {
        Map<String, String> result = Maps.newHashMap();
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(ip, DEFAULT_PORT));
            socket.getOutputStream().write(("mntr\n").getBytes());

            for (String line : CharStreams.readLines(
                new InputStreamReader(socket.getInputStream()))) {
                List<String> parts = Lists.newArrayList(Splitter.on("\t")
                    .trimResults()
                    .omitEmptyStrings()
                    .split(line));
                result.put(parts.get(0), parts.get(1));
            }
            return result;

        } catch (IOException e) {
            LOG.error(e, "Unable to retrieve metrics for server {}", ip);
            return ImmutableMap.of();

        } finally {
            if (socket.isConnected()) {
                socket.close();
            }
        }
    }
}
