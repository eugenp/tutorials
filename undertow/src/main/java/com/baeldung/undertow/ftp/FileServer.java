package com.baeldung.undertow.ftp;

import io.undertow.Undertow;
import io.undertow.server.handlers.resource.PathResourceManager;

import java.nio.file.Paths;

import static io.undertow.Handlers.resource;

public class FileServer {

    public static void main(String[] args) {
        Undertow server = Undertow.builder().addHttpListener(8080, "localhost")
          .setHandler(resource(new PathResourceManager(Paths.get(System.getProperty("user.home")), 100))
            .setDirectoryListingEnabled(true))
          .build();
        server.start();
    }

}
