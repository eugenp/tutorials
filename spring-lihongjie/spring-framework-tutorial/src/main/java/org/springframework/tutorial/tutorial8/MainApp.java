package org.springframework.tutorial.tutorial8;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"org/springframework/tutorial/tutorial8/beans.xml");
		
		Instrumentalist instrumentalist = (Instrumentalist) context.getBean("kenny");
		instrumentalist.getSong();
		instrumentalist.getInstrument().play();
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"org/springframework/tutorial/tutorial8/autowire-beans.xml");
		
		Instrumentalist instrumentalist2 = (Instrumentalist) ctx.getBean("kenny2");
		instrumentalist2.getSong();
		instrumentalist2.getInstrument().play();
		
	}
	
}
