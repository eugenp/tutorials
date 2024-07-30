package com.baeldung.quarkus.testcontainers.client;

import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/order")
@RegisterRestClient(configKey = "order-api")
public interface OrderService {

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    List<Order> findByCustomerId(@QueryParam("customerId") Long id);

}
