package com.baeldung.jersey.server;

import com.baeldung.jersey.client.filter.AddHeaderOnRequestFilter;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.sse.OutboundSseEvent;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseEventSink;

@Path("/echo-headers")
public class EchoHeaders {

    static final String REALM_KEY = "realm";
    static final String REALM_VALUE = "Baeldung";
    static final String QOP_KEY = "qop";
    static final String QOP_VALUE = "auth";
    static final String NONCE_KEY = "nonce";
    static final String NONCE_VALUE = "dcd98b7102dd2f0e8b11d0f600bfb0c093";
    static final String OPAQUE_KEY = "opaque";
    static final String OPAQUE_VALUE = "5ccc069c403ebaf9f0171e9517f40e41";
    static final String SSE_HEADER_KEY = "x-sse-header-key";

    @Context
    HttpHeaders headers;

    @GET
    public Response getHeadersBack() {
        return echoHeaders();
    }

    @RolesAllowed("ADMIN")
    @GET
    @Path("/digest")
    public Response getHeadersBackFromDigestAuthentication() {
        // As the Digest authentication require some complex steps to work we'll simulate the process
        // https://en.wikipedia.org/wiki/Digest_access_authentication#Example_with_explanation
        if (headers.getHeaderString("authorization") == null) {
            String authenticationRequired = "Digest " + REALM_KEY + "=\"" + REALM_VALUE + "\", " + QOP_KEY + "=\"" + QOP_VALUE + "\", " + NONCE_KEY + "=\"" + NONCE_VALUE + "\", " + OPAQUE_KEY + "=\"" + OPAQUE_VALUE + "\"";
            return Response.status(Response.Status.UNAUTHORIZED)
                    .header("WWW-Authenticate", authenticationRequired)
                    .build();
        } else {
            return echoHeaders();
        }
    }

    @GET
    @Path("/events")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void getServerSentEvents(@Context SseEventSink eventSink, @Context Sse sse) {
        OutboundSseEvent event = sse.newEventBuilder()
                .name("echo-headers")
                .data(String.class, headers.getHeaderString(AddHeaderOnRequestFilter.FILTER_HEADER_KEY))
                .build();
        eventSink.send(event);
    }

    private Response echoHeaders() {
        Response.ResponseBuilder responseBuilder = Response.noContent();

        headers.getRequestHeaders()
                .forEach((k, v) -> {
                    v.forEach(value -> responseBuilder.header(k, value));
                });

        return responseBuilder.build();
    }
}
