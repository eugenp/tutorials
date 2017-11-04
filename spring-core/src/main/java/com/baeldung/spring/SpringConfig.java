package com.baeldung.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.spring.beans.Car;
import com.baeldung.spring.beans.Truck;
import com.baeldung.spring.beans.Tyre;

@Configuration
@ComponentScan("com.bealdung.spring")
public class SpringConfig {

	@Bean
	public Tyre tyre() {
		return new Tyre("High Performance","175 mm");
	}

	@Bean
	public Car car(Tyre tyre) {
		return new Car(tyre);
	}

	@Bean
	public Truck truck() {
		return new Truck();
	}

}
