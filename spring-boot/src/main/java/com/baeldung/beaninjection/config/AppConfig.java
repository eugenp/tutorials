package com.baeldung.beaninjection.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.beaninjection.repository.CarRepository;
import com.baeldung.beaninjection.service.CarService;

@Configuration
public class AppConfig {

    @Bean
    public CarService carService() throws Exception {
        CarService carService = new CarService(carRepository());
        carService.setOptionalDependency("yeah");
        return carService;
    }

    @Bean
    public CarRepository carRepository() {
        return new CarRepository();
    }

}
