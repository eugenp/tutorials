package com.baeldung.jersey.timeout.server;

import java.util.concurrent.TimeUnit;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/timeout")
public class TimeoutResource {

    public static final long STALL = TimeUnit.SECONDS.toMillis(2l);

    @GET
    public String get() throws InterruptedException {
        Thread.sleep(STALL);
        return "processed";
    }
}
