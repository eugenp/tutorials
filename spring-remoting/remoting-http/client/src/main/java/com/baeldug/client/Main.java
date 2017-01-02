package com.baeldug.client;

import com.baeldung.api.CabBookingServer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

@Configuration
public class Main {

    @Bean
    public HttpInvokerProxyFactoryBean invoker() {
        HttpInvokerProxyFactoryBean invoker = new HttpInvokerProxyFactoryBean();
        invoker.setServiceUrl("http://localhost:9090/spring-remoting-http-server/account");
        invoker.setServiceInterface(CabBookingServer.class);
        return invoker;
    }

    @Bean
    public CabBookingClient client(CabBookingServer service){
        return new CabBookingClient(service);
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext rootContext =
                new AnnotationConfigApplicationContext();
        rootContext.scan(Main.class.getPackage().getName());
        rootContext.refresh();
        rootContext.getBean(CabBookingClient.class).run();
    }

}
