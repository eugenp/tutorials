package com.baeldung.mainclasstest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {
    /*
        //@Generated(value = "Spring Boot")
        @SuppressWarnings("unused")
        public static void main(String[] args) {

            SpringApplication app = new SpringApplication(Application.class);
            app.setBannerMode(Banner.Mode.OFF); app.setLogStartupInfo(false);
            app.setDefaultProperties(Collections.singletonMap("server.port", "8083")); app.run(args);
        }
     */
    public static void main(String[] args) {
        initializeApplication(args);
    }

    static ConfigurableApplicationContext initializeApplication(String[] args) {
        return SpringApplication.run(Application.class, args);
    }
}