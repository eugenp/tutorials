package com.baeldung.dependency.injectiontypes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.baeldung.dependency.injectiontypes")
class InjectionConfig {

	@Bean
	DaoFactoryWithSetterInjection daoFactoryWithSetterInjection() {
		return new DaoFactoryWithSetterInjection();
	}

	@Bean
	DaoFactoryWithConstructorInjection daoFactoryWithConstructorInjection() {
		return new DaoFactoryWithConstructorInjection(databaseDao(), fileDao());
	}

	@Bean
	DaoFactoryWithFieldInjection daoFactoryWithFieldInjection() {
		return new DaoFactoryWithFieldInjection();
	}

	@Bean
	Dao fileDao() {
		return new FileDao();
	}

	@Bean
	Dao databaseDao() {
		return new DatabaseDao();
	}

}
