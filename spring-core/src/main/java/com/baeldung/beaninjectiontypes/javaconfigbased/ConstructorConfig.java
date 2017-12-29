package com.baeldung.beaninjectiontypes.javaconfigbased;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.beaninjectiontypes.javaconfigbased.constructordomain.Department;
import com.baeldung.beaninjectiontypes.javaconfigbased.constructordomain.Type;

@Configuration
@ComponentScan("com.baeldung.beaninjectiontypes.javaconfigbased.constructordomain")
public class ConstructorConfig {

	@Bean
	public Department depaertment() {
		return new Department("Research");
	}

	@Bean
	public Type type() {
		return new Type("Permanent");
	}
}
