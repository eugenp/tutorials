package com.baeldung.spring.cloud.kubernetes.services.department;

import com.baeldung.spring.cloud.kubernetes.services.travelagency.controller.TravelAgencyController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TravelAgencyApplication implements CommandLineRunner  {

    private static final Log log = LogFactory.getLog(TravelAgencyController.class);

    public static void main(String[] args) {
        SpringApplication.run(TravelAgencyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Travel Agency Started! ");
    }

}
