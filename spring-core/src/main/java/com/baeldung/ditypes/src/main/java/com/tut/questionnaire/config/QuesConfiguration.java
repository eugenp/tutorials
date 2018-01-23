package com.tut.questionnaire.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.tut.questionnaire.QuestionService;
import com.tut.questionnaire.QuestionnaireController;


@ComponentScan("com.tut.questionnaire")
public class QuesConfiguration {
	@Bean
	public QuestionService questionService() {
		return new QuestionService();
	}
	
	@Bean
	public QuestionnaireController questionController() {
		return new QuestionnaireController();
	}
}
