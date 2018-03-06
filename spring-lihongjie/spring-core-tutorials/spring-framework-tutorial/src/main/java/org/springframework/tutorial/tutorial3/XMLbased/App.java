package org.springframework.tutorial.tutorial3.XMLbased;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(""
				+ "org/springframework/tutorial/tutorial3/XMLbased/spring-idol.xml"); 
		Performer performer = (Performer) context.getBean("duke");
		performer.perform();
		
		ApplicationContext context1 = new ClassPathXmlApplicationContext(""
				+ "org/springframework/tutorial/tutorial3/XMLbased/spring-idol.xml"); 
		Performer performer1 = (Performer) context1.getBean("duke1");
		performer1.perform();
		
		ApplicationContext context2 = new ClassPathXmlApplicationContext(""
				+ "org/springframework/tutorial/tutorial3/XMLbased/spring-idol.xml"); 
		Performer performer2 = (Performer) context1.getBean("duke2");
		performer2.perform();
		
		ApplicationContext context3 = new ClassPathXmlApplicationContext(""
				+ "org/springframework/tutorial/tutorial3/XMLbased/spring-idol.xml"); 
		Performer performer3 = (PoeticJuggler) context3.getBean("poeticDuke");
		performer3.perform();
		
		
		ApplicationContext context4 = new ClassPathXmlApplicationContext(""
				+ "org/springframework/tutorial/tutorial3/XMLbased/spring-idol.xml"); 
		Stage stage = (Stage) context4.getBean("theStage");
		stage.perform();
	}
}
