package ${package};

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("ping")
public class PingResource {

    @GET
    public Response get() {
        return Response.ok("${greeting-msg}").build();
    }

}