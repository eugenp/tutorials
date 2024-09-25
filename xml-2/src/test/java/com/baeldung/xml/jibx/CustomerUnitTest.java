package com.baeldung.xml.jibx;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static junit.framework.Assert.assertEquals;

public class CustomerUnitTest {

	@Test
	public void whenUnmarshalXML_ThenFieldsAreMapped() throws JiBXException, FileNotFoundException {
		IBindingFactory bfact = BindingDirectory.getFactory(Customer.class);
		IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("Customer1.xml");
		Customer customer = (Customer) uctx.unmarshalDocument(inputStream, null);

		assertEquals("Stefan Jaeger", customer.getPerson().getName());
		assertEquals("Davos Dorf", customer.getCity());

	}

	@Test
	public void WhenUnmarshal_ThenMappingInherited() throws JiBXException, FileNotFoundException {
		IBindingFactory bfact = BindingDirectory.getFactory(Customer.class);
		IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("Customer1.xml");
		Customer customer = (Customer) uctx.unmarshalDocument(inputStream, null);

		assertEquals(12345, customer.getPerson().getCustomerId());

	}

	@Test
	public void WhenUnmarshal_ThenPhoneMappingRead() throws JiBXException, FileNotFoundException {
		IBindingFactory bfact = BindingDirectory.getFactory(Customer.class);
		IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("Customer1.xml");
		Customer customer = (Customer) uctx.unmarshalDocument(inputStream, null);
		
		assertEquals("234678", customer.getHomePhone().getNumber());

	}
}
