package org.baeldung.spring.di.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BookTestBySetter {

	public static void main(String[] args) {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("beans.xml");
		 
        Book bookObj = (Book) appContext.getBean("book");
        System.out.println(bookObj.getBookTitle());
        System.out.println(bookObj.getAuthor().getAuthorName());
        System.out.println(bookObj.getAuthor().getAuthorEmail());
        System.out.println(bookObj.getPrice());

	}

}
