package com.baeldung.springbootsimple;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.web.servlet.support.*;

@SpringBootApplication
public class SpringBootTomcatApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootTomcatApplication.class, args);
    }
}
