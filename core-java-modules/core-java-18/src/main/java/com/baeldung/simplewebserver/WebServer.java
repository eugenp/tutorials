package com.baeldung.simplewebserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.util.function.Predicate;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpHandlers;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.Request;
import com.sun.net.httpserver.SimpleFileServer;

public class WebServer {

    private final InetSocketAddress address = new InetSocketAddress(8080);
    private final Path path = Path.of("/");

    public static void main(String[] args) {
        WebServer webServer = new WebServer();
        HttpServer server = webServer.createWithHandler401Response();
        server.start();
    }

    private HttpServer createBasic() {
        return SimpleFileServer.createFileServer(address, path, SimpleFileServer.OutputLevel.VERBOSE);
    }

    private HttpServer createWithHandler() throws IOException {
        HttpServer server = SimpleFileServer.createFileServer(address, path, SimpleFileServer.OutputLevel.VERBOSE);
        HttpHandler handler = SimpleFileServer.createFileHandler(Path.of("/Users"));
        server.createContext("/test", handler);
        return server;
    }

    private HttpServer createWithHandler401Response() {
        Predicate<Request> findAllowedPath = r -> r.getRequestURI()
          .getPath()
          .equals("/test/allowed");

        HttpHandler allowedResponse = HttpHandlers.of(200, Headers.of("Allow", "GET"), "Welcome");
        HttpHandler deniedResponse = HttpHandlers.of(401, Headers.of("Deny", "GET"), "Denied");

        HttpHandler handler = HttpHandlers.handleOrElse(findAllowedPath, allowedResponse, deniedResponse);

        HttpServer server = SimpleFileServer.createFileServer(address, path, SimpleFileServer.OutputLevel.VERBOSE);
        server.createContext("/test", handler);
        return server;
    }

    private HttpServer createWithFilter() throws IOException {
        Filter filter = SimpleFileServer.createOutputFilter(System.out, SimpleFileServer.OutputLevel.INFO);
        HttpHandler handler = SimpleFileServer.createFileHandler(Path.of("/Users"));
        return HttpServer.create(new InetSocketAddress(8080), 10, "/test", handler, filter);
    }

}
