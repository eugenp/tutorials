package com.baeldung.injection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.baeldung.injection.service.InjectConstructorService;
import com.baeldung.injection.service.InjectorAutowireService;
import com.baeldung.injection.service.InjectorSetterService;

@SpringBootApplication
@ComponentScan("com.baeldung")
public class InjectionApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(InjectionApplication.class, args);
		InjectConstructorService injector = context.getBean(InjectConstructorService.class);
		System.out.println(injector.getPerson());

		InjectorAutowireService injector2 = context.getBean(InjectorAutowireService.class);
		System.out.println(injector2.getPerson());

		InjectorSetterService injector3 = context.getBean(InjectorSetterService.class);
		System.out.println(injector3.getPerson());
	}
}
