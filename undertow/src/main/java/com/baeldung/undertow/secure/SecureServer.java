package com.baeldung.undertow.secure;

import io.undertow.Undertow;
import io.undertow.io.IoCallback;
import io.undertow.security.api.AuthenticationMechanism;
import io.undertow.security.api.AuthenticationMode;
import io.undertow.security.api.SecurityContext;
import io.undertow.security.handlers.AuthenticationCallHandler;
import io.undertow.security.handlers.AuthenticationConstraintHandler;
import io.undertow.security.handlers.AuthenticationMechanismsHandler;
import io.undertow.security.handlers.SecurityInitialHandler;
import io.undertow.security.idm.IdentityManager;
import io.undertow.security.impl.BasicAuthenticationMechanism;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecureServer {

    public static void main(String[] args) {
        final Map<String, char[]> users = new HashMap<>(2);
        users.put("root", "password".toCharArray());
        users.put("admin", "password".toCharArray());

        final IdentityManager idm = new CustomIdentityManager(users);

        Undertow server = Undertow.builder()
          .addHttpListener(8080, "localhost")
          .setHandler(addSecurity(SecureServer::setExchange, idm)).build();

        server.start();
    }

    private static void setExchange(HttpServerExchange exchange) {
        final SecurityContext context = exchange.getSecurityContext();
        exchange.getResponseSender().send("Hello " + context.getAuthenticatedAccount().getPrincipal().getName(), IoCallback.END_EXCHANGE);
    }

    private static HttpHandler addSecurity(final HttpHandler toWrap, final IdentityManager identityManager) {
        HttpHandler handler = toWrap;
        handler = new AuthenticationCallHandler(handler);
        handler = new AuthenticationConstraintHandler(handler);
        final List<AuthenticationMechanism> mechanisms = Collections
          .singletonList(new BasicAuthenticationMechanism("Baeldung_Realm"));
        handler = new AuthenticationMechanismsHandler(handler, mechanisms);
        handler = new SecurityInitialHandler(AuthenticationMode.PRO_ACTIVE, identityManager, handler);
        return handler;
    }

}
