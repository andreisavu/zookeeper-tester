package ro.pub.master.sii.zookeeper;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.bundles.AssetsBundle;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;
import ro.pub.master.sii.zookeeper.commands.DestroyClusterCommand;
import ro.pub.master.sii.zookeeper.commands.LaunchClusterCommand;
import ro.pub.master.sii.zookeeper.commands.RestartServicesCommand;
import ro.pub.master.sii.zookeeper.config.TesterConfiguration;
import ro.pub.master.sii.zookeeper.core.ManagedConsumer;
import ro.pub.master.sii.zookeeper.core.ManagedProducer;
import ro.pub.master.sii.zookeeper.core.ManagedZooKeeper;
import ro.pub.master.sii.zookeeper.health.HomeHealthCheck;
import ro.pub.master.sii.zookeeper.resources.HomeResource;
import ro.pub.master.sii.zookeeper.resources.InjectorResource;
import ro.pub.master.sii.zookeeper.resources.MetricsResource;
import ro.pub.master.sii.zookeeper.resources.NodeResource;

public class TesterService extends Service<TesterConfiguration> {

    public static void main(String[] args) throws Exception {
        new TesterService().run(args);
    }

    public TesterService() {
        super("byteman-tester");

        addBundle(new AssetsBundle());
        addBundle(new ViewBundle());

        addCommand(new LaunchClusterCommand());
        addCommand(new DestroyClusterCommand());
        addCommand(new RestartServicesCommand());
    }

    @Override
    protected void initialize(TesterConfiguration config,
                              Environment environment) throws Exception {

        NodeResource nodeResource = new NodeResource(config);
        environment.addResource(nodeResource);

        ManagedZooKeeper zookeeper = new ManagedZooKeeper(nodeResource.list());
        environment.manage(zookeeper);

        ManagedConsumer consumer = new ManagedConsumer(zookeeper);
        environment.manage(consumer);

        ManagedProducer producer = new ManagedProducer(zookeeper);
        environment.manage(producer);

        environment.addResource(new MetricsResource(consumer, producer));
        environment.addResource(new InjectorResource(config));
        environment.addResource(new HomeResource(nodeResource));

        environment.addHealthCheck(new HomeHealthCheck());
    }
}
