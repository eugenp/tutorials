package com.baeldung.helidon.se.security;

import io.helidon.config.Config;
import io.helidon.security.Security;
import io.helidon.security.SubjectType;
import io.helidon.security.provider.httpauth.HttpBasicAuthProvider;
import io.helidon.security.provider.httpauth.UserStore;
import io.helidon.security.webserver.WebSecurity;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerConfiguration;
import io.helidon.webserver.WebServer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WebApplicationSecurity {

    public static void main(String... args) throws Exception {

        Config config = Config.create();
        ServerConfiguration serverConfig =
                ServerConfiguration.fromConfig(config.get("server"));

        Map<String, MyUser> users = new HashMap<>();
        users.put("user", new MyUser("user", "user".toCharArray(), Arrays.asList("ROLE_USER")));
        users.put("admin", new MyUser("admin", "admin".toCharArray(), Arrays.asList("ROLE_USER", "ROLE_ADMIN")));
        UserStore store = user -> Optional.ofNullable(users.get(user));

        HttpBasicAuthProvider httpBasicAuthProvider = HttpBasicAuthProvider.builder()
                .realm("myRealm")
                .subjectType(SubjectType.USER)
                .userStore(store)
                .build();

        //1. Using Builder Pattern or Config Pattern
        Security security = Security.builder()
                .addAuthenticationProvider(httpBasicAuthProvider)
                .build();
        //Security security = Security.fromConfig(config);

        //2. WebSecurity from Security or from Config
        // WebSecurity webSecurity = WebSecurity.from(security)
        // .securityDefaults(WebSecurity.authenticate());

        WebSecurity webSecurity = WebSecurity.from(config);

        Routing routing = Routing.builder()
                .register(webSecurity)
                .get("/user", (request, response) -> response.send("Hello, I'm a Helidon SE user with ROLE_USER"))
                .get("/admin", (request, response) -> response.send("Hello, I'm a Helidon SE user with ROLE_ADMIN"))
                .build();

        WebServer webServer = WebServer.create(serverConfig, routing);

        webServer.start().thenAccept(ws ->
                System.out.println("Server started at: http://localhost:" + ws.port())
        );
    }
}
