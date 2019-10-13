package org.baeldung;

import org.junit.Test;

import com.baeldung.spring.bom.HelloWorldApp;

public class SpringContextIntegrationTest {

	@Test
    public final void testMain() throws Exception {
		HelloWorldApp.main(null);
	}
}
