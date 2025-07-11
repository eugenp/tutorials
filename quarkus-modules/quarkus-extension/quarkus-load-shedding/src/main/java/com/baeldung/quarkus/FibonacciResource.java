package com.baeldung.quarkus;

import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/fibonacci")
public class FibonacciResource {
    private static final Logger logger = LoggerFactory.getLogger(FibonacciResource.class);

    private final FibonacciService fibonacciService;

    public FibonacciResource(FibonacciService fibonacciService) {
        this.fibonacciService = fibonacciService;
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getFibonacciSequence(@QueryParam("iterations") Integer iterations) {
        if (iterations == null) {
            iterations = 10; //default value
        }
        logger.info("Received request with iterations: " + iterations);
        List<Integer> fibSequence = fibonacciService.generateSequence(iterations);
        return fibSequence.toString();
    }

    
    @PostConstruct
    public void startLoad() {
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            new Thread(() -> {
                while (true) {
                    Math.pow(Math.random(), Math.random()); // Keep CPU busy
                }
            }).start();
        }
    }
}
