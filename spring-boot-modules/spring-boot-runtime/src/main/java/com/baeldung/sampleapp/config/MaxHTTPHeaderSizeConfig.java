package com.baeldung.sampleapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan({ "com.baeldung.sampleapp.web" })
public class MaxHTTPHeaderSizeConfig implements WebMvcConfigurer {

	public MaxHTTPHeaderSizeConfig() {
		super();
	}

}
