package com.baeldung.quarkus.rbac.api;

import io.quarkus.security.PermissionsAllowed;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/another-secured")
public class SecureResource2Controller {

    private final SecurityIdentity securityIdentity;

    public SecureResource2Controller(SecurityIdentity securityIdentity) {
        this.securityIdentity = securityIdentity;
    }

    @GET
    @Path("/resource2/version")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PermissionsAllowed("Admin")
    public String get() {
        return "2.0.0";
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/resource2/message")
    @PermissionsAllowed(value = {"User", "Operator"}, inclusive = true)
    public Message message() {
        return new Message("Hello "+securityIdentity.getPrincipal().getName()+"!");
    }
}
