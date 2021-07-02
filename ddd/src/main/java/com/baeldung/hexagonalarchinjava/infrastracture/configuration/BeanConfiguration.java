package com.baeldung.hexagonalarchinjava.infrastracture.configuration;

import com.baeldung.hexagonalarchinjava.EventServiceApplication;
import com.baeldung.hexagonalarchinjava.domain.repository.EventRepository;
import com.baeldung.hexagonalarchinjava.domain.service.EventServiceImpl;
import com.baeldung.hexagonalarchinjava.domain.service.EventService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = EventServiceApplication.class)
public class BeanConfiguration {

	@Bean
	EventService eventService(final EventRepository orderRepository) {
		return new EventServiceImpl(orderRepository);
	}
}
