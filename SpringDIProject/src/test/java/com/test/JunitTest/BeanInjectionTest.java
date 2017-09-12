package com.test.JunitTest;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abir.spring.BeanTakingInjection;
import com.abir.spring.BeanToBeInjected;
import com.abir.spring.ConstructorBasedInjection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring-bean.xml" })
public class BeanInjectionTest {

	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			"spring-bean.xml");

	@Test
	public void testingBeanInjectedBySetter() {

		BeanTakingInjection testBean = (BeanTakingInjection) ctx
				.getBean("beanTakingInjection");
		assertNotNull(testBean.getBeantoBeInjected());
		assertEquals("class com.abir.spring.BeanToBeInjectedImpl", testBean
				.getBeantoBeInjected().getClass().toString());
		assertEquals("This is from injected Bean",
				testBean.callingInjectedBeanMethod());
	}

	@Test
	public void testingBeanInjectedByConstructor() {

		ConstructorBasedInjection testBeanConstructor = (ConstructorBasedInjection) ctx
				.getBean("constructorBasedInjection");
		assertNotNull(testBeanConstructor.getBeanToBeInjected());
		assertEquals("class com.abir.spring.BeanToBeInjectedImpl",
				testBeanConstructor.getBeanToBeInjected().getClass().toString());
		assertEquals("This is from injected Bean",
				testBeanConstructor.callingInjectedBeanMethod());
	}
}