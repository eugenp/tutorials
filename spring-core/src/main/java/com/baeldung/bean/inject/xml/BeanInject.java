package com.baeldung.bean.inject.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

public class BeanInject {
	final static Logger LOGGER = LoggerFactory.getLogger(BeanInject.class);

	public static void main(String[] args) {
		DefaultListableBeanFactory defaultFactory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(defaultFactory);
		reader.loadBeanDefinitions(new ClassPathResource("com.baeldung.bean.inject.xml"));
		Car b = (Car) defaultFactory.getBean("car");
		LOGGER.info("Car Been details: " + b.details());
	}
}