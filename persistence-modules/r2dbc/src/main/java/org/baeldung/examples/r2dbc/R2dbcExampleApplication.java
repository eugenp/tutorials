package org.baeldung.examples.r2dbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(R2DBCConfigurationProperties.class)
public class R2dbcExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(R2dbcExampleApplication.class, args);
    }
    
    

}
