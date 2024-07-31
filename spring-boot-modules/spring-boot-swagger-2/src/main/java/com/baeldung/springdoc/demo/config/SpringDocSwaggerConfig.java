package com.baeldung.springdoc.demo.config;

import java.util.Properties;

import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.stereotype.Component;

@Component
public class SpringDocSwaggerConfig implements ApplicationListener<ApplicationPreparedEvent> {

	@Override
	public void onApplicationEvent(final ApplicationPreparedEvent event) {
		ConfigurableEnvironment environment = event.getApplicationContext().getEnvironment();
		Properties props = new Properties();
		props.put("springdoc.swagger-ui.path", swaggerPath());
		environment.getPropertySources().addFirst(new PropertiesPropertySource("programmatically", props));
	}

	private String swaggerPath() {
		return "/myproject"; // TODO: implement your logic here.
	}
}