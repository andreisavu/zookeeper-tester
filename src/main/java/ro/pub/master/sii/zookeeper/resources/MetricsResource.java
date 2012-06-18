package ro.pub.master.sii.zookeeper.resources;

import ro.pub.master.sii.zookeeper.core.ManagedConsumer;
import ro.pub.master.sii.zookeeper.core.ManagedProducer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Random;

@Path("/metrics")
public class MetricsResource {

    private final ManagedConsumer consumer;

    public MetricsResource(ManagedConsumer consumer) {
        this.consumer = consumer;
    }

    @Path("latency")
    @GET
    public long latencyAverageOverLastSecond() {
        return consumer.getLatencyAverage();
    }
}
