package com.baeldung.jooby;

import static org.junit.Assert.assertEquals;

import org.jooby.test.MockRouter;
import org.junit.Test;

public class AppUnitTest {

	@Test
	public void given_defaultUrl_with_mockrouter_expect_fixedString() throws Throwable {
		String result = new MockRouter(new App()).get("/");
		assertEquals("Hello World!", result);
	}

}
