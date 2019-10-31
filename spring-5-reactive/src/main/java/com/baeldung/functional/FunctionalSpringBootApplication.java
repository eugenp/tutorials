package com.baeldung.functional;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.WebHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

import reactor.core.publisher.Flux;

@SpringBootApplication
@ComponentScan(basePackages = { "com.baeldung.functional" })
public class FunctionalSpringBootApplication {

    private static final Actor BRAD_PITT = new Actor("Brad", "Pitt");
    private static final Actor TOM_HANKS = new Actor("Tom", "Hanks");
    private static final List<Actor> actors = new CopyOnWriteArrayList<>(Arrays.asList(BRAD_PITT, TOM_HANKS));

    private RouterFunction<ServerResponse> routingFunction() {
        FormHandler formHandler = new FormHandler();

        RouterFunction<ServerResponse> restfulRouter = route(GET("/"),
            serverRequest -> ok().body(Flux.fromIterable(actors), Actor.class)).andRoute(POST("/"),
                serverRequest -> serverRequest.bodyToMono(Actor.class)
                    .doOnNext(actors::add)
                    .then(ok().build()));

        return route(GET("/test"), serverRequest -> ok().body(fromObject("helloworld")))
            .andRoute(POST("/login"), formHandler::handleLogin)
            .andRoute(POST("/upload"), formHandler::handleUpload)
            .and(RouterFunctions.resources("/files/**", new ClassPathResource("files/")))
            .andNest(path("/actor"), restfulRouter)
            .filter((request, next) -> {
                System.out.println("Before handler invocation: " + request.path());
                return next.handle(request);
            });
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() throws Exception {
        HttpHandler httpHandler = WebHttpHandlerBuilder.webHandler((WebHandler) toHttpHandler(routingFunction()))
            .filter(new IndexRewriteFilter())
            .build();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean<>(new RootServlet(httpHandler), "/");
        registrationBean.setLoadOnStartup(1);
        registrationBean.setAsyncSupported(true);
        return registrationBean;
    }

    public static void main(String[] args) {
        SpringApplication.run(FunctionalSpringBootApplication.class, args);
    }
}
