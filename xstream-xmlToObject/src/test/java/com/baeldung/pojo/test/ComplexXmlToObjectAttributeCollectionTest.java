package com.baeldung.pojo.test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.implicit.collection.pojo.ContactDetails;
import com.baeldung.implicit.collection.pojo.Customer;
import com.baeldung.initializer.SimpleXstreamInitializer;
import com.thoughtworks.xstream.XStream;

public class ComplexXmlToObjectAttributeCollectionTest {
	
	private XStream xstream = null;

	@Before
	public void dataSetup() {
		xstream = SimpleXstreamInitializer.getXstreamInstance();
		xstream.processAnnotations(Customer.class);
	}
	
	@Test
	public void convertXmlToObjectFromFile() {
		try {
			FileReader reader = new FileReader(new File("data-file-alias-implicit-collection.xml"));
			Customer customer = (Customer) xstream.fromXML(reader);
			Assert.assertNotNull(customer);
			Assert.assertNotNull(customer.getContactDetailsList());
			for(ContactDetails contactDetails : customer.getContactDetailsList()){
				Assert.assertNotNull(contactDetails.getContactType());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
