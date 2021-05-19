package com.baeldung;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.util.jndi.JndiContext;

import com.baeldung.model.MyBean;

/**
 * Hello world!
 *
 */
public class WireTapExample {
	public static void main(String[] args) throws Exception {
		JndiContext jndiContext = new JndiContext();
		jndiContext.bind("myBean", new MyBean());
		CamelContext camelContext = new DefaultCamelContext(jndiContext);
	}
}
