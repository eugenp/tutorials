package com.baeldung.spring.reactive.springreactiveexceptions.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeanConfiguration {
	@Value("${host:http://localhost:8080}")
	private String host;

	@Bean("webClient")
	public WebClient getSelfWebClient(WebClient.Builder builder) {
		return builder
		  .baseUrl(host)
		  .build();
	}

	@Bean("progWebClient")
	public WebClient getProgSelfWebClient() {
		return WebClient
		  .builder()
		  .baseUrl(host)
		  .exchangeStrategies(ExchangeStrategies
			.builder()
			.codecs(codecs -> codecs
			  .defaultCodecs()
			  .maxInMemorySize(500 * 1024))
			.build())
		  .build();
	}
}
