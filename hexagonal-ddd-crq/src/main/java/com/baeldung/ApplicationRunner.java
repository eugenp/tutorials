package com.baeldung;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.baeldung.application.controller.cli.ChgReqController;

@SpringBootApplication
public class ApplicationRunner implements CommandLineRunner {

    public final ChgReqController chgReqController;

    public ApplicationRunner(ChgReqController chgReqController) {
        this.chgReqController = chgReqController;
    }

    public static void main(final String[] args) {
        SpringApplication.run(ApplicationRunner.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        chgReqController.createChangeRequest();
    }
}
