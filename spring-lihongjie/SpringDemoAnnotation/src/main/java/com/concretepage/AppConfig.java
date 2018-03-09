package com.concretepage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.concretepage.bean.Address;
import com.concretepage.bean.Company;
@Configuration
@ComponentScan(basePackages="com.concretepage.bean")
public class AppConfig {
	@Bean
	public Address address(){
		Address address = new Address();
		address.setCity("Varanasi");
		return address;
	}
	@Bean
	public Company company() {
		Company company = new Company();
		company.setCompName("ABCD Ltd");
		return company;
	}
}
