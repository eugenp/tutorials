package com.baeldung.factorybean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FactoryBeanAppConfig {
    
<<<<<<< HEAD
    @Bean
=======
    @Bean(name = "tool")
>>>>>>> f354f9e3057481c76c10a1e2aa127d3abb95b329
    public ToolFactory toolFactory() {
        ToolFactory factory = new ToolFactory();
        factory.setFactoryId(7070);
        factory.setToolId(2);
        return factory;
    }
<<<<<<< HEAD

    @Bean
    public Tool tool() throws Exception {
        return toolFactory().getObject();
    }
=======
>>>>>>> f354f9e3057481c76c10a1e2aa127d3abb95b329
}
