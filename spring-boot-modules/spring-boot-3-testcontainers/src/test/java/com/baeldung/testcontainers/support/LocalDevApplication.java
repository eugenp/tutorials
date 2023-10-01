package com.baeldung.testcontainers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;
import com.baeldung.testcontainers.Application;


// Testcontainers require a valid docker installation.
// When running the app locally, ensure you have a valid Docker environment
class LocalDevApplication {

    public static void main(String[] args) {
        SpringApplication.from(Application::main)
          .with(LocalDevTestcontainersConfig.class)
          .run(args);
    }

    @TestConfiguration(proxyBeanMethods = false)
    static class LocalDevTestcontainersConfig {
        @Bean
        @RestartScope
        @ServiceConnection
        public MongoDBContainer mongoDBContainer() {
            return new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));
        }
    }

}
