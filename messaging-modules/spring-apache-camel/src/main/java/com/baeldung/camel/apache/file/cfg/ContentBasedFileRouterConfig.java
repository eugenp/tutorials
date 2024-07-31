package com.baeldung.camel.apache.file.cfg;

import java.util.Collections;
import java.util.List;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.camel.apache.file.ContentBasedFileRouter;

@Configuration
public class ContentBasedFileRouterConfig {

	@Bean
	ContentBasedFileRouter getContentBasedFileRouter() {
		return new ContentBasedFileRouter();
	}

	public List<RouteBuilder> routes() {
		return Collections.singletonList(getContentBasedFileRouter());
	}

}
