package com.baeldung.springdi;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class MyFood {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext appContext=new FileSystemXmlApplicationContext("spring-context.xml");
		BeanFactory factory=appContext;
		Food food=(Food)factory.getBean("food");
		food.myFood();

	}

}
