package com.baeldung.architecture.hexagon.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.baeldung.architecture.hexagon.framework.VehicleController;

@Configuration
public class AppConfiguration {

    @Bean
    public VehicleController vehicleController() {
        return new VehicleController();
    }
    
}