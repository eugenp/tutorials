package com.example.orth;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class LoadDatabase {

	Logger log = LoggerFactory.getLogger(LoadDatabase.class);
	
	@Bean
	CommandLineRunner initDatabase(SalesRepository repository) {
		return args -> {
			log.info("Preloading " +  repository.save(new CorporateSales("Axle", "Midwest", 250.15)));			
		};
	}
}
