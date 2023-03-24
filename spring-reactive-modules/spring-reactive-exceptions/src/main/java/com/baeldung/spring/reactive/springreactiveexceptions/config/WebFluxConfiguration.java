package com.baeldung.spring.reactive.springreactiveexceptions.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class WebFluxConfiguration implements WebFluxConfigurer {
	@Override
	public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
		configurer.defaultCodecs().maxInMemorySize(500 * 1024);
	}
}
