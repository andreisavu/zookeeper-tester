package ro.pub.master.sii.zookeeper.core;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.yammer.dropwizard.lifecycle.Managed;
import com.yammer.dropwizard.logging.Log;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.Set;

public class ManagedZooKeeper implements Managed, Watcher {

    private static final Log LOG = Log.forClass(ManagedZooKeeper.class);

    private final String connectString;

    private volatile ZooKeeper zookeeper;
    private volatile boolean connected;

    public ManagedZooKeeper(Set<Node> nodes) {
        this.connectString = Joiner.on(",").join(Iterables.transform(nodes,
            new Function<Node, String>() {
                @Override
                public String apply(Node node) {
                    return String.format("%s:2181", node.getHostname());
                }
            }));
        LOG.info("ZooKeeper connection string {}", connectString);
    }

    public ZooKeeper get() {
        return zookeeper;
    }

    public boolean isConnected() {
        return connected;
    }

    @Override
    public void start() throws Exception {
        LOG.info("Connecting to the ZooKeeper cluster");
        zookeeper = new ZooKeeper(connectString, 3000, this);

        LOG.info("Busy wait for the connection to be established");
        while (!isConnected()) {
            Thread.sleep(1000);
        }
    }

    @Override
    public void stop() throws Exception {
        LOG.info("Closing ZooKeeper connection");
        zookeeper.close();
    }

    @Override
    public void process(WatchedEvent event) {
        LOG.info("Got ZooKeeper event {} ", event);
        if (event.getType() == Event.EventType.None) {
            switch (event.getState()) {
                case SyncConnected:
                    connected = true;
                    break;
                case Expired:
                    connected = false;
                    break;
            }
        }
    }
}
