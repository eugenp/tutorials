package com.baeldung.ditypes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App
{
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("quesconfig.xml"); 
				//new AnnotationConfigApplicationContext(QuesConfiguration.class);
		QuestionnaireController client = context.getBean(QuestionnaireController.class);
		
		client.getQuestionService().getQuestion();
    }
}
