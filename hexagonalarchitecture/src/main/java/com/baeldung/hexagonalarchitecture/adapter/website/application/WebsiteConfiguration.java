package com.baeldung.hexagonalarchitecture.adapter.website.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.hexagonalarchitecture.adapter.inmemory.InMemoryRepository;
import com.baeldung.hexagonalarchitecture.core.repository.StudentApplicationRepository;
import com.baeldung.hexagonalarchitecture.core.service.StudentApplicationService;
import com.baeldung.hexagonalarchitecture.core.service.impl.DefaultStudentApplicationService;

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