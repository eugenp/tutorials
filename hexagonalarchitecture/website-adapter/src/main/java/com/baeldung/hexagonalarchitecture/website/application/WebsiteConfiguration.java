package com.baeldung.hexagonalarchitecture.website.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.hexagonalarchitecture.inmemory.adapter.InMemoryRepository;
import com.baeldung.hexagonalarchitecture.repository.StudentApplicationRepository;
import com.baeldung.hexagonalarchitecture.service.StudentApplicationService;
import com.baeldung.hexagonalarchitecture.service.impl.DefaultStudentApplicationService;

@Configuration
public class WebsiteConfiguration {

	@Bean
	public StudentApplicationRepository repository() {
		return new InMemoryRepository();
	}

	@Bean
	public StudentApplicationService applicationService() {
		return new DefaultStudentApplicationService();
	}
}