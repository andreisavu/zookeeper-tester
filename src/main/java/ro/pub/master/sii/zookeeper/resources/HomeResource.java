package ro.pub.master.sii.zookeeper.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import ro.pub.master.sii.zookeeper.views.HomeView;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class HomeResource {

    @GET
    public HomeView home() {
        return new HomeView();
    }

}
