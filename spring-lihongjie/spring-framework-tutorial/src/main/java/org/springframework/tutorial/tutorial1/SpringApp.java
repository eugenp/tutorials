package org.springframework.tutorial.tutorial1;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 *  第一个Spring 例子  Hello World
 *  @author: lihongjie
 */
public class SpringApp {
	
	public static void main(String[] args) throws IOException {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:org/springframework/tutorial/tutorial1/beans.xml"); // tutorial1/beans.xml 或者 /tutorial1/beans.xml
		
		

		HelloWorld hello = (HelloWorld) context.getBean("helloWorld");
		
//		System.out.print(" " + res.getFilename() + res.contentLength());
		hello.printMessage();
		hello.getMessage();
		
		//
		ApplicationContext context1 = new  FileSystemXmlApplicationContext("classpath:org/springframework/tutorial/tutorial1/beans.xml");
		HelloWorld hello1 = (HelloWorld) context1.getBean("helloWorld");
		hello1.printMessage();
		
		
		

		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppContext.class);
		HelloWorld hello2 = ctx.getBean(HelloWorld.class);
		hello2.printMessage();
		hello2.getMessage();

	}

}
