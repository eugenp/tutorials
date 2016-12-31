package com.baeldung;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;

@Configuration
public class DispatcherConfig {

    @Bean(name = "/account") HttpInvokerServiceExporter accountService() {
        HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
        exporter.setService( new GreetingsServiceImpl() );
        exporter.setServiceInterface( GreetingsService.class );
        return exporter;
    }

}
