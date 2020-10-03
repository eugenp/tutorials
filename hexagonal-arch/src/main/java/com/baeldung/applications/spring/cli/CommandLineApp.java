package com.baeldung.applications.spring.cli;

import com.baeldung.domain.entities.Car;
import com.baeldung.domain.ports.in.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CommandLineApp implements ApplicationRunner {

    private static final Logger cliLogger = LoggerFactory.getLogger(CommandLineApp.class);

    @Autowired
    private CarService carService;

    @Override
    public void run(ApplicationArguments args)  {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            cliLogger.info("\nEnter the Vehicle Identification Number: ");
            String vin = scanner.next();
            Car car = carService.getCar(vin);
            if (car == null) {
                cliLogger.info("\nCar not found...");
                continue;
            }
            String carDetailsFormat = "\nBrand: %s\nModel Year:%d\n";
            String carDetails = String.format(
                    carDetailsFormat,
                    car.getBrand(),
                    car.getModelYear()
                    );
            cliLogger.info(carDetails);
        }
    }
}
