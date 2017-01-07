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
        invoker.setServiceUrl("http://localhost:9090/spring-remoting-http-server/account");
        invoker.setServiceInterface(CabBookingService.class);
        return invoker;
    }

    @Bean
    public CabBookingClient client(CabBookingService service){
        return new CabBookingClient(service);
    }

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext rootContext =
                new AnnotationConfigApplicationContext();
        rootContext.scan(Main.class.getPackage().getName());
        rootContext.refresh();
        CabBookingClient bean = rootContext.getBean(CabBookingClient.class);
        for (int i = 0; i < 1000; i++) {
            bean.run();
            Thread.sleep(1000);
        }
    }

}
