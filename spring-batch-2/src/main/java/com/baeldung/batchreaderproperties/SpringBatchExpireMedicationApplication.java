package com.baeldung.batchreaderproperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:disable-job-autorun.properties")
public class SpringBatchExpireMedicationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchExpireMedicationApplication.class, args);
    }

}
