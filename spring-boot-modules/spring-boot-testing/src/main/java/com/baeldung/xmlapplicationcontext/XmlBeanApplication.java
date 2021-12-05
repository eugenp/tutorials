package com.baeldung.xmlapplicationcontext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath*:application-context.xml"})
public class XmlBeanApplication {

	public static void main(String[] args) {
		SpringApplication.run(XmlBeanApplication.class, args);
	}

}
