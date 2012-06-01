package ro.pub.master.sii.zookeeper.commands;

import com.yammer.dropwizard.AbstractService;
import com.yammer.dropwizard.cli.ConfiguredCommand;
import org.apache.commons.cli.CommandLine;
import org.apache.whirr.ClusterController;
import org.apache.whirr.ClusterSpec;
import ro.pub.master.sii.zookeeper.config.TesterConfiguration;

public class RestartServicesCommand extends ConfiguredCommand<TesterConfiguration> {

    public RestartServicesCommand() {
        super("restart-services", "Restart ZooKeeper on all nodes");
    }

    @Override
    protected void run(AbstractService<TesterConfiguration> service,
                       TesterConfiguration config, CommandLine params) throws Exception {
        ClusterController controller = new ClusterController();

        ClusterSpec spec = config.getClusterSpec();
        controller.stopServices(spec);
        controller.startServices(spec);

        System.exit(0); /* force application exit */
    }
}
