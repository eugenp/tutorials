package org.baeldung.dependency.injection.config;

import org.baeldung.dependency.injection.Address;
import org.baeldung.dependency.injection.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	
	@Bean
	public Address address(){
		Address address = new Address();
		address.setCity("Chennai");
		address.setCountry("India");
		return address;
	}

	@Bean
	public Employee employeeConstructorInjection(){
		return new Employee(address());
	}
	
	
	@Bean
	public Employee employeeSetterInjection(){
		return new Employee();
	}
}
