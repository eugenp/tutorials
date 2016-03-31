package com.baeldung.pojo.test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.initializer.SimpleXstreamInitializer;
import com.baeldung.pojo.Customer;
import com.thoughtworks.xstream.XStream;

public class XmlToObjectFieldAliasTest {
	
	private XStream xstream = null;

	@Before
	public void dataSetup() {
		xstream = SimpleXstreamInitializer.getXstreamInstance();
		xstream.alias("customer" , Customer.class);
		xstream.aliasField("fn", Customer.class, "firstName");
	}
	
	@Test
	public void convertXmlToObjectFromFile() {
		try {
			FileReader reader = new FileReader(new File("data-file-alias-field.xml"));
			Customer customer = (Customer) xstream.fromXML(reader);
			Assert.assertNotNull(customer);
			Assert.assertNotNull(customer.getFirstName());
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
