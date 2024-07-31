package com.baeldung.oauth2.resource.server.secure;

import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/resource")
@RequestScoped
public class ProtectedResource {

    @Inject
    private JsonWebToken principal;

    @GET
    @RolesAllowed("resource.read")
    @Path("/read")
    public Response read() {
        //DoStaff
        return Response.ok("Hello, " + principal.getName()).build();
    }

    @POST
    @RolesAllowed("resource.write")
    @Path("/write")
    public Response write() {
        //DoStaff
        return Response.ok("Hello, " + principal.getName())
                .header("location", UUID.randomUUID().toString())
                .build();
    }
}
