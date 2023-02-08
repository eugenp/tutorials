package com.baeldung.akka;

import akka.actor.ActorSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@ComponentScan
public class AppConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public ActorSystem actorSystem() {
        ActorSystem system = ActorSystem.create("akka-spring-demo");
        SpringExtension.SPRING_EXTENSION_PROVIDER.get(system).initialize(applicationContext);
        return system;
    }

    static Logger LOGGER = LoggerFactory.getLogger(AppConfiguration.class);

    public static void main(String[] args) {
        LOGGER.debug("THIS IS DEBUG LEVEL");
        LOGGER.info("THIS IS INFO LEVEL");
        LOGGER.warn("THIS IS WARD LEVEL");
        LOGGER.error("THIS IS ERROR LEVEL");
    }

}
