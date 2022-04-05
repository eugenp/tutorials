package com.baeldung.kubernetes.admission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.baeldung.kubernetes.admission.config.AdmissionControllerProperties;

@SpringBootApplication
@EnableConfigurationProperties(AdmissionControllerProperties.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
