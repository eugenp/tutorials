package com.baeldung.mathematics;

import jakarta.ws.rs.ext.Provider;
import io.quarkus.load.shedding.RequestPrioritizer;
import io.quarkus.load.shedding.RequestPriority;
import io.vertx.core.http.impl.HttpServerRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class LoadRequestPrioritizer implements RequestPrioritizer<HttpServerRequestWrapper> {
    private static final Logger logger = LoggerFactory.getLogger(LoadRequestPrioritizer.class);

    @Override
    public boolean appliesTo(Object request) {
        return request instanceof HttpServerRequestWrapper;
    }

    @Override
    public RequestPriority priority(HttpServerRequestWrapper request) {
        String requestPath = request.path();
        if (requestPath.contains("fibonacci")) {
        } else {
            return RequestPriority.NORMAL;
        }
    }
}
