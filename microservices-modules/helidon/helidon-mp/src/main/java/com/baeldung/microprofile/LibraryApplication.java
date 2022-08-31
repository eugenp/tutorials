package com.baeldung.microprofile;

import com.baeldung.microprofile.web.BookEndpoint;
import io.helidon.common.CollectionsHelper;
import io.helidon.microprofile.server.Server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

@ApplicationPath("/library")
public class LibraryApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return CollectionsHelper.setOf(BookEndpoint.class);
    }

    public static void main(String... args) {
        Server server = Server.builder()
                .addApplication(LibraryApplication.class)
                .port(9080)
                .build();
        server.start();
    }
}
