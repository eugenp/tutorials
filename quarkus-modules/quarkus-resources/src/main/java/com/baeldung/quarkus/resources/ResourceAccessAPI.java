package com.baeldung.quarkus.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Path("/resources")
public class ResourceAccessAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceAccessAPI.class);

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getHello() throws IOException {
        return Response.ok("Hello Quarkus!").build();
    }

    @GET
    @Path("/default")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getDefaultResource() throws IOException {
        return Response.ok(readResource("default-resource.txt")).build();
    }

    @GET
    @Path("/default-nested")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getDefaultNestedResource() throws IOException {
        return Response.ok(readResource("text/another-resource.txt")).build();
    }

    @GET
    @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJsonResource() throws IOException {
        return Response.ok(readResource("config.json")).build();
    }


    private String readResource(String resourcePath) throws IOException {
        LOGGER.info("Reading resource from path: {}", resourcePath);
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath)) {
            if (in == null) {
                LOGGER.error("Resource not found at path: {}", resourcePath);
                throw new IOException("Resource not found: " + resourcePath);
            }
            LOGGER.info("Successfully read resource: {}", resourcePath);
            return new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)).lines()
                .collect(Collectors.joining("\n"));
        }
    }
}
