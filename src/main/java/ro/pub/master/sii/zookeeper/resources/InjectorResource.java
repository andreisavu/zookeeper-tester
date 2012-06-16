package ro.pub.master.sii.zookeeper.resources;

import com.yammer.dropwizard.logging.Log;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/injector")
public class InjectorResource {

    private static Log LOG = Log.forClass(InjectorResource.class);

    @POST
    @Path("enable")
    public Response enableInjector() {
        LOG.info("Injecting network faults on remote cluster");
        // TODO: submit byteman rule
        return Response.ok().build();
    }

    @POST
    @Path("disable")
    public Response disableInjector() {
        LOG.info("Stopping injector");
        // TODO: remove byteman rule
        return Response.ok().build();
    }
}
