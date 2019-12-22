package com.baeldung.spring.cloud.kubernetes.travelagency;

import com.baeldung.spring.cloud.kubernetes.travelagency.controller.TravelAgencyController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Log log = LogFactory.getLog(TravelAgencyController.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public void run(String... args) throws Exception {
        log.info("Travel Agency Started! ");
    }

}
