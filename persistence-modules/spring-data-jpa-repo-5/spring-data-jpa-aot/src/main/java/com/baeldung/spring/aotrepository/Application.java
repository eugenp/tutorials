package com.baeldung.spring.aotrepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.JavaVersion;

@SpringBootApplication
public class Application {

    private static final Log logger = LogFactory.getLog(Application.class);

    public static void main(String[] args) {
        logger.info("Application starts..");
        logger.info("Java version: " + System.getProperty("java.version"));
        logger.info("Java version: " + JavaVersion.getJavaVersion());

        SpringApplication.run(Application.class, args);
    }
}
