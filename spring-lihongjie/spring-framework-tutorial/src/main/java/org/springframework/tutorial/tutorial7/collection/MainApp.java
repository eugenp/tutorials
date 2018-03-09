package org.springframework.tutorial.tutorial7.collection;

import java.awt.List;
import java.util.Collection;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.tutorial.tutorial7.Instrument;

public class MainApp {

	public static void main(String[] args) {
		
		AbstractApplicationContext context = new ClassPathXmlApplicationContext(
				"org/springframework/tutorial/tutorial7/collection/beans.xml");
		OneManBand one = (OneManBand) context.getBean("oneManBand");
		Collection<Instrument> list = one.getInstruments();

	}
}
