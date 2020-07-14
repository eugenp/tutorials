package com.baeldung.hexagonal;

import com.baeldung.hexagonal.controller.MainController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * User: raychenon
 * Date: 12/7/2020
 */

@SpringBootApplication
@ComponentScan(basePackageClasses = MainController.class)
public class MainApp {

    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }

}
