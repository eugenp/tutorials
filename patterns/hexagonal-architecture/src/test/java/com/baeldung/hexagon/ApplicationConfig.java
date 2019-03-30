package com.baeldung.hexagon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.adapter.NotifyConsoleAdapter;
import com.baeldung.adapter.UserRepositoryFileAdapter;
import com.baeldung.port.NotifyPort;
import com.baeldung.port.UserRepositoryPort;

@Configuration
public class ApplicationConfig {
	
	@Bean
	public BirthdayService birthdayService() {
		return new BirthdayService();
	}
	
	@Bean
	public UserRepositoryPort userRepositoryPort() {
		return new UserRepositoryFileAdapter();
	}
	
	@Bean
	public NotifyPort notifyPort() {
		return new NotifyConsoleAdapter();
	}
	
	
}
