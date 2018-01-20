package com.tut.questionnaire.config;

@ComponentScan
public class QuesConfiguration {
	@Bean
	public QuestionnaireController getQuestionnaireController(QuestionService questionService) {
		return new questionnaireController(questionService);
	}
}
