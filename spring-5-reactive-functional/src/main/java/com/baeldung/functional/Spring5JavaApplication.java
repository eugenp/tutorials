package com.baeldung.functional;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;

import com.baeldung.functional.model.CityAirport;
import com.baeldung.functional.repository.FurthestAirportRepository;

@SpringBootApplication
public class Spring5JavaApplication {

    public static void main(String[] args) throws IOException {
        
        new SpringApplicationBuilder(Spring5JavaApplication.class).initializers(new ApplicationContextInitializer<GenericApplicationContext>() {

            @Override
            public void initialize(GenericApplicationContext applicationContext) {
                
                // Functional bean registration for furthest airport repository, where as nearest airport is annotation avsed   
                List<CityAirport> furthestCityNameAirports = Arrays.asList(new CityAirport("Nigeria", "Suvarnabhumi"), new CityAirport("Japan", "BIA"));
                applicationContext.registerBean(FurthestAirportRepository.class, () -> new FurthestAirportRepository(furthestCityNameAirports));
            }
            
        }).run(args);
    }
}