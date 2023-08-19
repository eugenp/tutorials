package com.baeldung.helidon.se.security;

import io.helidon.config.Config;
import io.helidon.security.Security;
import io.helidon.security.SubjectType;
import io.helidon.security.integration.webserver.WebSecurity;
import io.helidon.security.providers.httpauth.HttpBasicAuthProvider;
import io.helidon.security.providers.httpauth.SecureUserStore;
import io.helidon.webserver.Routing;
import io.helidon.webserver.WebServer;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WebApplicationSecurity {

    public static void main(String... args) {

        Config config = Config.create();

        Map<String, MyUser> users = new HashMap<>();
        users.put("user", new MyUser("user", "user".toCharArray(), Collections.singletonList("ROLE_USER")));
        users.put("admin", new MyUser("admin", "admin".toCharArray(), Arrays.asList("ROLE_USER", "ROLE_ADMIN")));
        SecureUserStore store = user -> Optional.ofNullable(users.get(user));

        HttpBasicAuthProvider httpBasicAuthProvider = HttpBasicAuthProvider.builder()
                .realm("myRealm")
                .subjectType(SubjectType.USER)
                .userStore(store)
                .build();

        //1. Using Builder Pattern or Config Pattern
        Security security = Security.builder()
                .addAuthenticationProvider(httpBasicAuthProvider)
                .build();
        //Security security = Security.create(config);

        //2. WebSecurity from Security or from Config
        // WebSecurity webSecurity = WebSecurity.create(security).securityDefaults(WebSecurity.authenticate());

        WebSecurity webSecurity = WebSecurity.create(config);

        Routing routing = Routing.builder()
                .register(webSecurity)
                .get("/user", (request, response) -> response.send("Hello, I'm a Helidon SE user with ROLE_USER"))
                .get("/admin", (request, response) -> response.send("Hello, I'm a Helidon SE user with ROLE_ADMIN"))
                .build();

        WebServer webServer = WebServer.create(routing, config.get("server"));

        webServer.start().thenAccept(ws ->
                System.out.println("Server started at: http://localhost:" + ws.port())
        );
    }
}
