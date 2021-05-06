package com.baeldung.filtersinterceptors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer   {

	private final LogInterceptor logInterceptor;
	
	public WebMvcConfig(LogInterceptor logInterceptor) {
		this.logInterceptor = new LogInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(logInterceptor).addPathPatterns("/**");
	}
	
}
