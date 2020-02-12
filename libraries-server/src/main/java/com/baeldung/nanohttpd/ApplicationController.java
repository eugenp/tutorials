package com.baeldung.nanohttpd;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;

import java.io.IOException;

public class ApplicationController extends RouterNanoHTTPD {

    ApplicationController() throws IOException {
        super(8072);
        addMappings();
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
    }

    @Override
    public void addMappings() {
        addRoute("/", IndexHandler.class);
        addRoute("/users", UserHandler.class);
    }

    public static class UserHandler extends DefaultHandler {
        @Override
        public String getText() {
            return "UserA, UserB, UserC";
        }

        @Override
        public String getMimeType() {
            return MIME_PLAINTEXT;
        }

        @Override
        public Response.IStatus getStatus() {
            return Response.Status.OK;
        }
    }
}