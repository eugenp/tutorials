package com.baeldung.applications.spring.config;

import com.baeldung.domain.ports.in.CarService;
import com.baeldung.domain.ports.out.CarDao;
import com.baeldung.domain.services.CarServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CarServiceAdapterConfig {

    @Bean
    CarService carService(CarDao carDao) {
        return new CarServiceImpl(carDao);
    }
}
