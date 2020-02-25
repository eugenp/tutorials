package com.hexagonal.example.configuration;

import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
	@Bean
	public ArrayList<Integer> getArrayList() {
		return new ArrayList<Integer>();
	}

}
