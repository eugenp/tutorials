package com.baeldung.bean.inject.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BeanInjectionSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeanInjectionSpringApplication.class, args);
	}

	@Bean
	public Wheel getWheel() {
		return new Wheel("Tube-Less", "Michelin");
	}

	// We need to initialize AudioSystem Been as It is a set as required in Car.
	@Bean
	public AudioSystem getAudio() {
		return new AudioSystem("SoundLink", "Bose Automotive");
	}

	@Bean
	public Car getCar() {
		// need to inject 'Wheel' Been as its a must to initialize Car
		return new Car(getWheel());
	}
}
