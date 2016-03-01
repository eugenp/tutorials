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
	private XStream xstream = null;

	@Before
	public void dataSetup() {
		customer = SimpleDataGeneration.generateData();
		xstream = SimpleXstreamInitializer.getXstreamInstance();
		xstream.processAnnotations(Customer.class);
		xstream.processAnnotations(AddressDetails.class);
		xstream.processAnnotations(ContactDetails.class);
		xstream.omitField(Customer.class , "firstName");
		xstream.registerConverter(new MyDateConverter());
		//xtream.registerConverter(new MySingleValueConverter());
		xstream.aliasField("fn", Customer.class, "firstName");
		
		dataXml = xstream.toXML(customer);
		System.out.println(dataXml);
	}

	@Test
	public void convertDataToXml() {
		Assert.assertNotNull(dataXml);
	}

	@Test
	public void convertXmlToObject() {
		customer = (Customer) xstream.fromXML(dataXml);
		Assert.assertNotNull(customer);
	}

}
