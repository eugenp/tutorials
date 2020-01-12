package com.baeldung.dddhexagonalquickexample.configuration;

import com.baeldung.dddhexagonalquickexample.CarSellingApplication;
import com.baeldung.dddhexagonalquickexample.business.adapter.CustomCarStoreService;
import com.baeldung.dddhexagonalquickexample.domain.ports.service.ICarStoreService;
import com.baeldung.dddhexagonalquickexample.infraestructure.repository.CarStoreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = CarSellingApplication.class)
public class BeanConfiguration {

	@Autowired
	private CarStoreRepository repository;
	
    @Bean
    ICarStoreService carStoreService() {
        return new CustomCarStoreService(repository);
    }
}
