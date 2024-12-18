package com.baeldung.quarkus.websockets;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/cdi-controller")
public class CdiController {
    @Inject
    private CdiService cdiService;

    @GET
    public String getConnection() {
        return cdiService.getConnectionId();
    }

    @POST
    public void broadcast(String payload) {
        cdiService.sendToAll(payload);
    }
}
