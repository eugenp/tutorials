package com.baeldug.client;

import com.baeldung.api.CabBookingService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

@Configuration
public class Main {

    @Bean
    public HttpInvokerProxyFactoryBean invoker() {
        HttpInvokerProxyFactoryBean invoker = new HttpInvokerProxyFactoryBean();
        invoker.setServiceUrl("http://localhost:9090/spring-remoting-http-server/booking");
        invoker.setServiceInterface(CabBookingService.class);
        return invoker;
    }

    @Bean
    public CabBookingClient client(CabBookingService service){
        return new CabBookingClient(service);
    }

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext rootContext =
                new AnnotationConfigApplicationContext();
        rootContext.scan(Main.class.getPackage().getName());
        rootContext.refresh();
        CabBookingClient bean = rootContext.getBean(CabBookingClient.class);
        bean.run();
    }

}
