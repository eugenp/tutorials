 package com.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


@EnableAutoConfiguration
@ComponentScan(basePackages =
{
    "com.baeldung"
})
@Configuration
@ImportResource("classpath:user-applicationcontext.xml")
public class UserApp {
	
	 public static void main(String[] args) throws Exception {
	        SpringApplication.run(UserApp.class, args);
	    }

}
