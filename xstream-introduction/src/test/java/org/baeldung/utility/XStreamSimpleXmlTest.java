package org.baeldung.utility;

import org.baeldung.initializer.SimpleXstreamInitializer;
import org.baeldung.pojo.AddressDetails;
import org.baeldung.pojo.ContactDetails;
import org.baeldung.pojo.Customer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

public class XStreamSimpleXmlTest {

	private Customer customer = null;
	private String dataXml = null;
	private XStream xtream = null;

	@Before
	public void dataSetup() {
		customer = SimpleDataGeneration.generateData();
		xtream = SimpleXstreamInitializer.getXstreamInstance();
		xtream.processAnnotations(Customer.class);
		xtream.processAnnotations(AddressDetails.class);
		xtream.processAnnotations(ContactDetails.class);
		xtream.omitField(Customer.class , "firstName");
		xtream.registerConverter(new MyDateConverter());
		//xtream.registerConverter(new MySingleValueConverter());
		xtream.aliasField("fn", Customer.class, "firstName");
		
		dataXml = xtream.toXML(customer);
		System.out.println(dataXml);
	}

	@Test
	public void convertDataToXml() {
		Assert.assertNotNull(dataXml);
	}

	@Test
	public void convertXmlToObject() {
		customer = (Customer) xtream.fromXML(dataXml);
		Assert.assertNotNull(customer);
	}

}
