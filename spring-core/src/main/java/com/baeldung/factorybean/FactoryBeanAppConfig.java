package com.baeldung.factorybean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FactoryBeanAppConfig {
    @Bean
    public ToolFactory tool() {
        ToolFactory factory = new ToolFactory();
        factory.setFactoryId(7070);
        factory.setToolId(2);
        factory.setToolName("wrench");
        factory.setToolPrice(3.7);
        return factory;
    }

    @Bean
    public Worker worker() throws Exception {
        Worker worker = new Worker();
        worker.setNumber("1002");
        worker.setTool(tool().getObject());
        return worker;
    }
}
