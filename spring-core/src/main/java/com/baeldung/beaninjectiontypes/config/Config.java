package com.baeldung.beaninjectiontypes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.beaninjectiontypes.services.MessageService;

@Configuration
@ComponentScan(value = { "com.baeldung.beaninjectiontypes.constructorbased", "com.baeldung.beaninjectiontypes.setterbased" })
public class Config {

	@Bean
	public MessageService getMessageService() {
		return new MessageService();
	}
}
