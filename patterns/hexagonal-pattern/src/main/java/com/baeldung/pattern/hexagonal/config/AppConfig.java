package com.baeldung.pattern.hexagonal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.pattern.hexagonal.domain.repository.MobileStoreRepository;
import com.baeldung.pattern.hexagonal.domain.repository.MobileStoreRepositoryImpl;
import com.baeldung.pattern.hexagonal.domain.services.MobileStoreService;
import com.baeldung.pattern.hexagonal.domain.services.MobileStoreServiceImpl;

@Configuration
public class AppConfig {

	@Bean
	public MobileStoreService getMobileService(MobileStoreRepository mobileStoreRepository)
	{
		return new MobileStoreServiceImpl(mobileStoreRepository);
	}
	
	@Bean
	public MobileStoreRepository getMobileRepository()
	{
		return new MobileStoreRepositoryImpl();
	}
}
