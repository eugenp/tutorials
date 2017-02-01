package com.baeldung.configuration;

import com.baeldung.settervsconstructordi.BusService;
import com.baeldung.settervsconstructordi.CarService;
import com.baeldung.settervsconstructordi.EngineService;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContextTestSetterVsGetterInjection {

    @Bean
    public EngineService engineService() {
        EngineService engineService = new EngineService();
        return engineService;
    }

    @Bean
    public CarService carService() {
        CarService carService = new CarService(engineService());
        return carService;
    }

    @Bean
    public BusService busService() {
        BusService busService = new BusService();
        Whitebox.setInternalState(busService, "engineService", engineService());
        return busService;
    }
}