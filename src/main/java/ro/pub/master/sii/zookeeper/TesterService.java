package ro.pub.master.sii.zookeeper;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.bundles.AssetsBundle;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;
import ro.pub.master.sii.zookeeper.commands.DestroyClusterCommand;
import ro.pub.master.sii.zookeeper.commands.LaunchClusterCommand;
import ro.pub.master.sii.zookeeper.config.TesterConfiguration;
import ro.pub.master.sii.zookeeper.health.HomeHealthCheck;
import ro.pub.master.sii.zookeeper.resources.HomeResource;

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
    }

    @Override
    protected void initialize(TesterConfiguration testerConfiguration,
                              Environment environment) throws Exception {
        environment.addResource(new HomeResource());
        environment.addHealthCheck(new HomeHealthCheck());
    }
}