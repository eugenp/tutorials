package com.baeldung.server;

import com.baeldung.api.CabService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;

@Configuration
public class DispatcherConfig {

    @Bean(name = "/account") HttpInvokerServiceExporter accountService() {
        HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
        exporter.setService( new CabServiceImpl() );
        exporter.setServiceInterface( CabService.class );
        return exporter;
    }

}
