package com.baeldung.reactive.security;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

@ComponentScan(basePackages = {"com.baeldung.reactive.security"})
@EnableWebFlux
public class SpringSecurity5Application {

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(SpringSecurity5Application.class)) {
            context.getBean(DisposableServer.class).onDispose().block();
        }
    }

    @Bean
    public DisposableServer nettyContext(ApplicationContext context) {
        HttpHandler handler = WebHttpHandlerBuilder.applicationContext(context)
                .build();
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(handler);
        return HttpServer.create().host("localhost").port(8080).handle(adapter).bind().block();
    }

}
