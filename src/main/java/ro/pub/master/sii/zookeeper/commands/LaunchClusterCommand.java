package ro.pub.master.sii.zookeeper.commands;

import com.yammer.dropwizard.AbstractService;
import com.yammer.dropwizard.cli.ConfiguredCommand;
import org.apache.commons.cli.CommandLine;
import org.apache.whirr.ClusterController;
import org.apache.whirr.ClusterSpec;
import ro.pub.master.sii.zookeeper.config.TesterConfiguration;

public class LaunchClusterCommand extends ConfiguredCommand<TesterConfiguration> {

    public LaunchClusterCommand() {
        super("launch-cluster", "Launch ZooKeeper test cluster");
    }

    @Override
    protected void run(AbstractService<TesterConfiguration> service,
                       TesterConfiguration config, CommandLine params) throws Exception {
        ClusterController controller = new ClusterController();
        controller.launchCluster(config.getClusterSpec());
    }
}
