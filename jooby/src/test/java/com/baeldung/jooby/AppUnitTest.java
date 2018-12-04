package com.baeldung.jooby;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import org.jooby.test.JoobyRule;
import org.jooby.test.MockRouter;
import org.junit.ClassRule;
import org.junit.Test;

public class AppUnitTest {

	@ClassRule
	public static JoobyRule app = new JoobyRule(new App());

	@Test
	public void given_defaultUrl_expect_fixedString() {
		get("/").then().assertThat().body(equalTo("Hello World!")).statusCode(200)
				.contentType("text/html;charset=UTF-8");
	}

	@Test
	public void given_defaultUrl_with_mockrouter_expect_fixedString() throws Throwable {
		String result = new MockRouter(new App()).get("/");
		assertEquals("Hello World!", result);
	}

}
