package com.baeldung.restexpress;

import io.netty.handler.codec.http.HttpMethod;
import org.restexpress.RestExpress;

public abstract class Routes {
    public static void define(Configuration config, RestExpress server) {
        // TODO: Your routes here...
        server.uri("/samples/uuid/{uuid}.{format}", config.getSampleUuidEntityController())
          .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
          .name(Constants.Routes.SINGLE_UUID_SAMPLE);

        server.uri("/samples/uuid.{format}", config.getSampleUuidEntityController())
          .action("readAll", HttpMethod.GET)
          .method(HttpMethod.POST)
          .name(Constants.Routes.SAMPLE_UUID_COLLECTION);

        server.uri("/samples/oid/{uuid}.{format}", config.getSampleOidEntityController())
          .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
          .name(Constants.Routes.SINGLE_OID_SAMPLE);

        server.uri("/samples/oid.{format}", config.getSampleOidEntityController())
          .action("readAll", HttpMethod.GET)
          .method(HttpMethod.POST)
          .name(Constants.Routes.SAMPLE_OID_COLLECTION);

        // or REGEX matching routes...
        // server.regex("/some.regex", config.getRouteController());
    }
}
