package com.abir.mainmethod;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.abir.spring.BeanTakingInjection;
import com.abir.spring.ConstructorBasedInjection;

public class Main {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"spring-bean.xml");
		BeanTakingInjection testBean = (BeanTakingInjection) ctx
				.getBean("beanTakingInjection");
		ConstructorBasedInjection testBeanConstructor = (ConstructorBasedInjection) ctx
				.getBean("constructorBasedInjection");

		testBean.callingInjectedBeanMethod();
		testBeanConstructor.callingInjectedBeanMethod();

	}
}