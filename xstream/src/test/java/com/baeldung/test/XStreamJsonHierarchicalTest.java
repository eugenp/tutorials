package com.baeldung.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.initializer.SimpleXstreamInitializer;
import com.baeldung.pojo.ContactDetails;
import com.baeldung.pojo.Customer;
import com.baeldung.utility.SimpleDataGeneration;
import com.thoughtworks.xstream.XStream;

public class XStreamJsonHierarchicalTest {

	private Customer customer = null;
	private String dataJson = null;
	private XStream xstream = null;

	@Before
	public void dataSetup() {
		SimpleXstreamInitializer simpleXstreamInitializer = new SimpleXstreamInitializer();
		xstream = simpleXstreamInitializer.getXstreamJsonHierarchicalInstance();
		xstream.processAnnotations(Customer.class);
	}

	@Test
	public void convertObjectToJson() {
		customer = SimpleDataGeneration.generateData();
		xstream.alias("customer", Customer.class);
		xstream.alias("contactDetails", ContactDetails.class);
		xstream.aliasField("fn", Customer.class, "firstName");
		dataJson = xstream.toXML(customer);
		System.out.println(dataJson);
		Assert.assertNotNull(dataJson);
	}

	@Test
	public void convertJsonToObject() {
		customer = SimpleDataGeneration.generateData();
		dataJson = xstream.toXML(customer);
		// customer = (Customer) xstream.fromXML(dataJson);
		// Assert.assertNotNull(customer);
	}

}
