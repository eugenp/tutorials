package com.baeldung.pojo.test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.initializer.SimpleXstreamInitializer;
import com.baeldung.pojo.Customer;
import com.baeldung.utility.SimpleDataGeneration;
import com.thoughtworks.xstream.XStream;

public class XmlToObjectTest {
	
	private XStream xstream = null;

	@Before
	public void dataSetup() {
		xstream = SimpleXstreamInitializer.getXstreamInstance();
	}
	
	@Test
	public void convertXmlToObjectFromFile() {
		try {
			FileReader reader = new FileReader(new File("data-file.xml"));
			Customer customer = (Customer) xstream.fromXML(reader);
			Assert.assertNotNull(customer);
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	@Test
	public void convertXmlToObjectFromString() {
		Customer customer = SimpleDataGeneration.generateData();
		String dataXml = xstream.toXML(customer);
		Customer convertedCustomer = (Customer) xstream.fromXML(dataXml);
		Assert.assertNotNull(convertedCustomer);
	}
	

}
