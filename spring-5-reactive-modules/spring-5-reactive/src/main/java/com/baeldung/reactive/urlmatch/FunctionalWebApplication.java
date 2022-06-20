package com.baeldung.reactive.urlmatch;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.server.WebServer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ServletHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.WebHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

import reactor.core.publisher.Flux;

public class FunctionalWebApplication {

    private static final Actor BRAD_PITT = new Actor("Brad", "Pitt");
    private static final Actor TOM_HANKS = new Actor("Tom", "Hanks");
    private static final List<Actor> actors = new CopyOnWriteArrayList<>(Arrays.asList(BRAD_PITT, TOM_HANKS));

    private RouterFunction<ServerResponse> routingFunction() {
        FormHandler formHandler = new FormHandler();

        RouterFunction<ServerResponse> restfulRouter = route(GET("/"), serverRequest -> ok().body(Flux.fromIterable(actors), Actor.class)).andRoute(POST("/"), serverRequest -> serverRequest.bodyToMono(Actor.class)
            .doOnNext(actors::add)
            .then(ok().build()));

        return route(GET("/test"), serverRequest -> ok().body(fromValue("helloworld"))).andRoute(POST("/login"), formHandler::handleLogin)
            .andRoute(POST("/upload"), formHandler::handleUpload)
            .and(RouterFunctions.resources("/files/**", new ClassPathResource("files/")))
            .andNest(path("/actor"), restfulRouter)
            .filter((request, next) -> {
                System.out.println("Before handler invocation: " + request.path());
                return next.handle(request);
            });
    }

    WebServer start() throws Exception {
        WebHandler webHandler = (WebHandler) toHttpHandler(routingFunction());
        HttpHandler httpHandler = WebHttpHandlerBuilder.webHandler(webHandler)
            .filter(new IndexRewriteFilter())
            .build();

        Tomcat tomcat = new Tomcat();
        tomcat.setHostname("localhost");
        tomcat.setPort(9090);
        Context rootContext = tomcat.addContext("", System.getProperty("java.io.tmpdir"));
        ServletHttpHandlerAdapter servlet = new ServletHttpHandlerAdapter(httpHandler);
        Tomcat.addServlet(rootContext, "httpHandlerServlet", servlet);
        rootContext.addServletMappingDecoded("/", "httpHandlerServlet");

        TomcatWebServer server = new TomcatWebServer(tomcat);
        server.start();
        return server;

    }

    public static void main(String[] args) {
        try {
            new FunctionalWebApplication().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
