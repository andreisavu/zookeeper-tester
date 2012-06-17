package ro.pub.master.sii.zookeeper.core;

import com.yammer.dropwizard.lifecycle.Managed;

public class ManagedConsumer implements Managed {

    private ManagedZooKeeper zookeeper;

    public ManagedConsumer(ManagedZooKeeper zookeeper) {
        this.zookeeper = zookeeper;
    }

    @Override
    public void start() throws Exception {
    }

    @Override
    public void stop() throws Exception {
    }
}
