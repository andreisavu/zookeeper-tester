package ro.pub.master.sii.zookeeper.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Random;

@Path("/metrics")
public class MetricsResource {

    private final Random random = new Random();

    @Path("latency")
    @GET
    public int latencyAverageOverLastSecond() {
        return random.nextInt(100);
    }

    @Path("throughput")
    @GET
    public int throughputAverageOverLastSecond() {
        return random.nextInt(100);
    }
}
