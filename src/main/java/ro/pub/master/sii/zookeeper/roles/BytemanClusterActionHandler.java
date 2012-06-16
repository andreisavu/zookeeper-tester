package ro.pub.master.sii.zookeeper.roles;

import org.apache.whirr.service.ClusterActionEvent;
import org.apache.whirr.service.ClusterActionHandlerSupport;

import static org.jclouds.scriptbuilder.domain.Statements.call;

public class BytemanClusterActionHandler extends ClusterActionHandlerSupport {

    public static final String ROLE = "byteman";

    public String getRole() {
        return ROLE;
    }

    @Override
    public void beforeBootstrap(ClusterActionEvent event) {
        addStatement(event, call("install_byteman"));
    }
}
