package com.baeldung.beaninjection.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.beaninjection.model.Car;
import com.baeldung.beaninjection.model.CarFactory;
import com.baeldung.beaninjection.repository.CarRepository;
import com.baeldung.beaninjection.service.CarService;

@Configuration
public class AppConfig {

    @Bean
    public CarService userService() throws Exception {
        CarService carService = new CarService(carRepository());
        carService.setOptionalDependency("yeah");
        carService.setCar(car());
        return carService;
    }

    @Bean
    public CarRepository carRepository() {
        return new CarRepository();
    }

    @Bean
    public CarFactory carFactory() {
        return new CarFactory();
    }

    @Bean
    public Car car() throws Exception {
        return carFactory().createInstance();
    }
}
