package org.springframework.tutorial.tutorial1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppContext {
	
	@Bean
	public HelloWorld helloworld() {
		HelloWorld helloworld = new HelloWorld();
		helloworld.setMessage("Hello Lihongjie");
		return helloworld;
	}

}
