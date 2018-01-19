package com.baeldung.ditypes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.tut.questionnaire.QuestionService;


@ComponentScan("com.tut.questionnaire")
public class QuesConfiguration {
	@Bean
	public QuestionService questionService() {
		return new QuestionService();
	}
}
