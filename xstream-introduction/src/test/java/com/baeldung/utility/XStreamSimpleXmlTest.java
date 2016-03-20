package com.baeldung.utility;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.initializer.SimpleXstreamInitializer;
import com.baeldung.pojo.AddressDetails;
import com.baeldung.pojo.ContactDetails;
import com.baeldung.pojo.Customer;
import com.baeldung.utility.SimpleDataGeneration;
import com.thoughtworks.xstream.XStream;

public class XStreamSimpleXmlTest {

	private Customer customer = null;

	private String dataXml = null;

	private XStream xstream = null;

	@Before
	public void dataSetup() {
		customer = SimpleDataGeneration.generateData();
		xstream = SimpleXstreamInitializer.getXstreamInstance();
		xstream.processAnnotations(Customer.class);
		xstream.processAnnotations(AddressDetails.class);
		xstream.processAnnotations(ContactDetails.class);
		xstream.omitField(Customer.class , "lastName");
		xstream.registerConverter(new MyDateConverter());
		// xstream.registerConverter(new MySingleValueConverter());
		xstream.aliasField("fn" , Customer.class , "firstName");

		dataXml = xstream.toXML(customer);
		System.out.println(dataXml);
	}

	@Test
	public void testClassAliasedAnnotation() {
		Assert.assertNotEquals(-1 , dataXml.indexOf("<customer>"));
	}

	@Test
	public void testFieldAliasedAnnotation() {
		Assert.assertNotEquals(-1 , dataXml.indexOf("<fn>"));
	}

	@Test
	public void testImplicitCollection() {
		Assert.assertEquals(-1 , dataXml.indexOf("contactDetailsList"));
	}

	@Test
	public void testDateFieldFormating() {
		Assert.assertEquals("14-02-1986" , dataXml.substring(dataXml.indexOf("<dob>") + 5 , dataXml.indexOf("</dob>")));
	}

	@Test
	public void testOmitField() {
		Assert.assertEquals(-1 , dataXml.indexOf("lastName"));
	}
}
