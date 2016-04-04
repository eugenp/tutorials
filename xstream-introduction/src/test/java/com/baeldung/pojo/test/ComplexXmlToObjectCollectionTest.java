package com.baeldung.pojo.test;

import java.io.FileReader;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.implicit.collection.pojo.Customer;
import com.baeldung.initializer.SimpleXstreamInitializer;
import com.thoughtworks.xstream.XStream;

public class ComplexXmlToObjectCollectionTest {
	
	private XStream xstream = null;

	@Before
	public void dataSetup() {
		SimpleXstreamInitializer simpleXstreamInitializer = new SimpleXstreamInitializer();
		xstream = simpleXstreamInitializer.getXstreamInstance();
		xstream.processAnnotations(Customer.class);
	}
	
	@Test
	public void convertXmlToObjectFromFile() {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			FileReader reader = new FileReader(classLoader.getResource("data-file-alias-implicit-collection.xml").getFile());
			Customer customer = (Customer) xstream.fromXML(reader);
			Assert.assertNotNull(customer);
			Assert.assertNotNull(customer.getContactDetailsList());
			//System.out.println(customer);
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
