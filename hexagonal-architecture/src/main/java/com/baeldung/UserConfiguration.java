package com.baeldung;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.core.impl.UserServiceImpl;
import com.baeldung.port.inbound.UserService;
import com.baeldung.port.outbound.UserRepository;

@Configuration
public class UserConfiguration {

	@Bean
	public UserService getUserService(@Qualifier("jpaRepo") UserRepository userRepository) {
		return new UserServiceImpl(userRepository);
	}
	
	/*
	 * @Bean public UserService getUserService(@Qualifier("mapRepo") UserRepository
	 * userRepository) { return new UserServiceImpl(userRepository); }
	 */
}
