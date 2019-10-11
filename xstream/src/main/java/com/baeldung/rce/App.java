package com.baeldung.rce;

import com.sun.net.httpserver.HttpServer;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

/**
 * Web application which is intentionally vulnerable to an XStream remote code
 * exploitation (RCE).
 *
 * <p>
 * This test application is meant to maintain a set of {@link Person} models. It
 * exposes a "/persons" endpoint which supports the following operations:
 *
 * <ol>
 * <li>{@code POST} XML for adding a new {@link Person} to the set
 * <li>{@code GET} for retrieving the set of {@link Person} models as XML
 * </ol>
 *
 * The {@code POST} handler is vulnerable to an RCE exploit.
 */
public final class App {

    public static App createHardened(int port) {
        final XStream xstream = new XStream();
        xstream.addPermission(NoTypePermission.NONE);
        xstream.addPermission(NullPermission.NULL);
        xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
        xstream.allowTypes(new Class<?>[] { Person.class });
        return new App(port, xstream);
    }

    public static App createVulnerable(int port) {
        return new App(port, new XStream());
    }

    private final int port;
    private final Set<Person> persons;
    private final XStream xstream;
    private HttpServer server;

    private App(int port, XStream xstream) {
        this.port = port;
        persons = new HashSet<>();
        // this app is vulnerable because XStream security is not configured
        this.xstream = xstream;
        this.xstream.alias("person", Person.class);
    }

    void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress("localhost", port), 0);
        server.createContext("/persons", exchange -> {
            switch (exchange.getRequestMethod()) {
            case "POST":
                final Person person = (Person) xstream.fromXML(exchange.getRequestBody());
                persons.add(person);
                exchange.sendResponseHeaders(201, 0);
                exchange.close();
                break;
            case "GET":
                exchange.sendResponseHeaders(200, 0);
                xstream.toXML(persons, exchange.getResponseBody());
                exchange.close();
                break;
            default:
                exchange.sendResponseHeaders(405, 0);
                exchange.close();
            }
        });
        server.start();
    }

    void stop() {
        if (server != null) {
            server.stop(0);
        }
    }

    int port() {
        if (server == null)
            throw new IllegalStateException("Server not started");
        return server.getAddress()
            .getPort();
    }
}
