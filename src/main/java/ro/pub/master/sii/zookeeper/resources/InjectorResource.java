package ro.pub.master.sii.zookeeper.resources;

import com.google.common.base.Predicates;
import com.yammer.dropwizard.logging.Log;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.whirr.ClusterController;
import org.apache.whirr.ClusterSpec;
import org.apache.whirr.state.ClusterStateStore;
import org.jclouds.compute.RunScriptOnNodesException;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.scriptbuilder.domain.Statements;
import ro.pub.master.sii.zookeeper.config.TesterConfiguration;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.util.Map;

import static org.jclouds.scriptbuilder.domain.Statements.call;
import static org.jclouds.scriptbuilder.domain.Statements.exec;

@Path("/injector")
public class InjectorResource {

    private static Log LOG = Log.forClass(InjectorResource.class);

    private ClusterController controller;
    private ClusterSpec spec;

    public InjectorResource(TesterConfiguration config) throws ConfigurationException {
        this.controller = new ClusterController();
        this.spec = config.getClusterSpec();
    }

    @POST
    @Path("enable")
    public Response enableInjector() throws RunScriptOnNodesException, IOException {
        Map response = this.controller.runScriptOnNodesMatching(spec,
            Predicates.<NodeMetadata>alwaysTrue(), exec(". /etc/profile && " +
            "cd /usr/local/byteman && " +
            "./bin/bmsubmit.sh readPayload.btm && " +
            "./bin/bmsubmit.sh"));
        LOG.info(response.toString());
        return Response.ok().build();
    }

    @POST
    @Path("disable")
    public Response disableInjector() throws RunScriptOnNodesException, IOException {
        Map response = this.controller.runScriptOnNodesMatching(spec,
            Predicates.<NodeMetadata>alwaysTrue(), exec(". /etc/profile && " +
            "cd /usr/local/byteman && " +
            "./bin/bmsubmit.sh -u readPayload.btm && " +
            "./bin/bmsubmit.sh"));
        LOG.info(response.toString());
        return Response.ok().build();
    }
}
