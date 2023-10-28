package com.baeldung.reactive.urlmatch;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;
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

public class ExploreSpring5URLPatternUsingRouterFunctions {

    private RouterFunction<ServerResponse> routingFunction() {

        return route(GET("/t?st"), serverRequest -> ok().body(fromValue("Path /t?st is accessed"))).andRoute(GET("/test/{*id}"), serverRequest -> ok().body(fromValue(serverRequest.pathVariable("id"))))
            .andRoute(GET("/baeldung/*Id"), serverRequest -> ok().body(fromValue("/baeldung/*Id path was accessed")))
            .andRoute(GET("/{var1}_{var2}"), serverRequest -> ok().body(fromValue(serverRequest.pathVariable("var1") + " , " + serverRequest.pathVariable("var2"))))
            .andRoute(GET("/{baeldung:[a-z]+}"), serverRequest -> ok().body(fromValue("/{baeldung:[a-z]+} was accessed and baeldung=" + serverRequest.pathVariable("baeldung"))))
            .and(RouterFunctions.resources("/files/{*filepaths}", new ClassPathResource("files/")))
            .and(RouterFunctions.resources("/resources/**", new ClassPathResource("resources/")));
    }

    WebServer start() {
        WebHandler webHandler = (WebHandler) toHttpHandler(routingFunction());
        HttpHandler httpHandler = WebHttpHandlerBuilder.webHandler(webHandler)
            .filter(new IndexRewriteFilter())
            .build();

        Tomcat tomcat = new Tomcat();
        tomcat.setHostname("localhost");
        tomcat.setPort(9090);
        Context rootContext = tomcat.addContext("", System.getProperty("java.io.tmpdir"));
        ServletHttpHandlerAdapter servlet = new ServletHttpHandlerAdapter(httpHandler);
        Wrapper servletWrapper = Tomcat.addServlet(rootContext, "httpHandlerServlet", servlet);
        servletWrapper.setAsyncSupported(true);
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
