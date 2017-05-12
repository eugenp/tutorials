package com.baeldung.spring.dependency.injection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.spring.dependency.injection.beans.Language;
import com.baeldung.spring.dependency.injection.beans.Writer;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:spring-beans.xml")
public class SongAppJavaConfig {

	@Bean
	public Writer writer() {
		Writer writer = new Writer();
		writer.setName("Adele");
		return writer;
	}

	@Bean
	public Language language() {
		Language language = new Language();
		language.setName("English");
		return language;
	}
}
