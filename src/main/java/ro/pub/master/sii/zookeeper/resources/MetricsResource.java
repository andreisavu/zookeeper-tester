package ro.pub.master.sii.zookeeper.resources;

import ro.pub.master.sii.zookeeper.core.ManagedConsumer;
import ro.pub.master.sii.zookeeper.core.ManagedProducer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Random;

@Path("/metrics")
public class MetricsResource {

    private final Random random = new Random();

    private final ManagedConsumer consumer;
    private final ManagedProducer producer;

    public MetricsResource(ManagedConsumer consumer, ManagedProducer producer) {
        this.consumer = consumer;
        this.producer = producer;
    }

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
