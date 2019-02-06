package com.baeldung.examples.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.baeldung.examples.common.BookService;
import com.baeldung.examples.common.BookServiceImpl;

@Configuration
@Import({ SpringBeansConfig.class })
@ComponentScan("com.baeldung.examples")
public class SpringMainConfig {

	@Bean
	public BookService bookServiceGenerator() {
		return new BookServiceImpl();
	}

}
