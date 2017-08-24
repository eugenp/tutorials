package com.baeldung.setterdi.annotation.dhaval;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

	@Bean(name = "subject")
	public Subject subject() {
		Subject sub = new Subject();
		sub.setLesson(lesson());
		return sub;
	}

	@Bean(name = "lesson")
	public Lesson lesson() {
		Lesson les = new Lesson();
		les.setMessage1("Hello World");
		return les;
	}
}
