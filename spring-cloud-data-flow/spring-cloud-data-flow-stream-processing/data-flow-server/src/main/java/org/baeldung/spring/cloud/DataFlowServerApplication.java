package org.baeldung.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.dataflow.server.EnableDataFlowServer;

@EnableDataFlowServer
@SpringBootApplication
public class DataFlowServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataFlowServerApplication.class, args);
    }
}
