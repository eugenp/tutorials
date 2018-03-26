package com.baeldung.spring.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.spring.core.Car;
import com.baeldung.spring.core.Department;
import com.baeldung.spring.core.Employee;

@Configuration
public class AppConfig {

	@Bean
	public Department getDepartment() {
		return new Department("Sales", "S01");
	}

	@Bean
	public Employee getEmployee() {
		return new Employee(getDepartment());
	}

	@Bean
	public Car getCar() {
		Car car = new Car();
		car.setCarName("Honda");
		car.setCarType("SUV");
		return car;
	}
}
