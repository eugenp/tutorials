package com.baeldung.application;

import com.baeldung.application.defaultconfig.ConfigA;
import com.baeldung.application.defaultconfig.ConfigB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BeanOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(new Class[]{BeanOrderApplication.class, ConfigA.class, ConfigB.class},
                args);
    }

}
