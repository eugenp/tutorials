package com.baeldung.accesing_session_attributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringWebApplicationInitializer {

    private Logger log = LoggerFactory.getLogger(getClass());

    public SpringWebApplicationInitializer() {
        super();
        String encoding = System.getProperty("file.encoding");
        log.info(encoding);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringWebApplicationInitializer.class, args);
    }
}
