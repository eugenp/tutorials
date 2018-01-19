package com.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.demo.impl.School;
import com.demo.impl.SchoolImpl;

@Configuration
@ComponentScan(basePackages="com.demo.config")
public class AppConfig {

	@Bean
	   public School getSchool() {
	      return new SchoolImpl();
	   }
}
