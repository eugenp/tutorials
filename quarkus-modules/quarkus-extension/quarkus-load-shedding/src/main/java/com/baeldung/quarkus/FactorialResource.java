package com.baeldung.quarkus;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/factorial")
public class FactorialResource {
    private static final Logger logger = LoggerFactory.getLogger(FactorialResource.class);

    private final FactorialService factorialService;

    public FactorialResource(FactorialService factorialService) {
        this.factorialService = factorialService;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getFactorialSequence(@QueryParam("iterations") Integer iterations) {
        if (iterations == null) {
            iterations = 10;
        }
        logger.info("Generating factorial sequence with [" + iterations + "] iterations.");
        List<Long> factorialSequence = factorialService.generateSequence(iterations);
        return factorialSequence.toString();
    }
}
