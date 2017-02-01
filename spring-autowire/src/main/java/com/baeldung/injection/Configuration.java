package com.baeldung.injection;

import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

	//Object which is needed by Square class constructor
	//Used by Constructor injection
	@Bean
	public SquareProperties createSquareProperties(){
		return new SquareProperties(4);
	}
	
	//Object which is needed by Square color property. 
	//Used by Setter injection
	// Enum having RED
	@Bean
	public Color addProperty(){
		return Color.RED;
	}
}
