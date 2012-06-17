package ro.pub.master.sii.zookeeper.core;

import com.yammer.dropwizard.lifecycle.Managed;

public class ManagedProducer implements Managed {

    private final ManagedZooKeeper zookeeper;

    public ManagedProducer(ManagedZooKeeper zookeeper) {
        this.zookeeper = zookeeper;
    }

    public void start() throws Exception {
    }

    public void stop() throws Exception {
    }
}
