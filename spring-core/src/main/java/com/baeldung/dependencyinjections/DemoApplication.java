package com.baeldung.dependencyinjections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Entry point for spring boot
 * @author hemant
 *
 */
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws Exception  {
		ApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);
		System.out.println(ctx.getBean(Email.class));
	}
}
