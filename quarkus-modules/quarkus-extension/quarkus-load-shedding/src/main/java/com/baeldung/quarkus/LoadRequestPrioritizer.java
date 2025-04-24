package com.baeldung.quarkus;

import jakarta.ws.rs.ext.Provider;
import io.quarkus.load.shedding.RequestPrioritizer;
import io.quarkus.load.shedding.RequestPriority;
import io.vertx.core.http.impl.HttpServerRequestWrapper;

@Provider
public class LoadRequestPrioritizer implements RequestPrioritizer<HttpServerRequestWrapper> {

    @Override
    public boolean appliesTo(Object request) {
        return request instanceof HttpServerRequestWrapper;
    }

    @Override
    public RequestPriority priority(HttpServerRequestWrapper request) {
        String requestPath = request.path();
        if (requestPath.contains("fibonacci")) {
            return RequestPriority.CRITICAL;
        } else {
            return RequestPriority.NORMAL;
        }
    }
}
