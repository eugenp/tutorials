package com.baeldung.hexagonal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.baeldung.hexagonal.database.impl.TimeSheetPersistencePortImpl;
import com.baeldung.hexagonal.ports.TimeSheetPersistencePort;
import com.baeldung.hexagonal.ports.TimeSheetServicePort;
import com.baeldung.hexagonal.services.TimeSheetService;

@SpringBootApplication
public class TimeSheetApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeSheetApplication.class, args);
	}
	
	
    @Bean
    public TimeSheetPersistencePort timeSheetPersistencePort(){
        return new TimeSheetPersistencePortImpl();
    }

    @Bean
    public TimeSheetServicePort timeSheetService(){
        return new TimeSheetService(timeSheetPersistencePort());
    }

}
