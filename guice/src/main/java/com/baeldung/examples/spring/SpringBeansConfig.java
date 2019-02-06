package com.baeldung.examples.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.examples.common.AudioBookService;
import com.baeldung.examples.common.AudioBookServiceImpl;

@Configuration
public class SpringBeansConfig {

	@Bean
	public AudioBookService audioBookServiceGenerator() {
		return new AudioBookServiceImpl();
	}

}
