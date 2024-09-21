package com.baeldung.nanohttpd;

import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;

public class ItemGetController extends NanoHTTPD {

    ItemGetController() throws IOException {
        super(8071);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
    }

    @Override
    public Response serve(IHTTPSession session) {
        if (session.getMethod() == Method.GET) {
            String itemIdRequestParam = session.getParameters().get("itemId").get(0);
            return newFixedLengthResponse("Requested itemId = " + itemIdRequestParam);
        }
        return newFixedLengthResponse(Response.Status.NOT_FOUND, MIME_PLAINTEXT, "The requested resource does not exist");
    }
}