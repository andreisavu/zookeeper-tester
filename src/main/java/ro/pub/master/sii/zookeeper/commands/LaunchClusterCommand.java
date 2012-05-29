package ro.pub.master.sii.zookeeper.commands;

import com.yammer.dropwizard.AbstractService;
import com.yammer.dropwizard.cli.ConfiguredCommand;
import org.apache.commons.cli.CommandLine;
import ro.pub.master.sii.zookeeper.config.TesterConfiguration;

public class LaunchClusterCommand extends ConfiguredCommand<TesterConfiguration> {

    public LaunchClusterCommand() {
        super("launch-cluster", "Launch ZooKeeper test cluster");
    }

    @Override
    protected void run(AbstractService<TesterConfiguration> testerConfigurationAbstractService,
                       TesterConfiguration testerConfiguration, CommandLine commandLine) throws Exception {
        System.err.println("Not implemented.");
    }
}
