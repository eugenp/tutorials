package com.tut.questionnaire;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App
{
	public static void main(String[] args) {
		BeanFactory beanfactory = new AnnotationConfigApplicationContext(QuesConfiguration.class);
		QuestionnaireController client = beanfactory.getBean(QuestionnaireController.class);
		
		client.getQuestionService();
    }
}
