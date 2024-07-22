package com.baeldung.quarkus.testcontainers;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Path("/order")
public class OrderResource {

    @Inject
    OrderService orderService;

    @GET
    public List<Order> findByCustomerId(@QueryParam("customerId") Long id) {
        return orderService.findByCustomerId(id);
    }

}
