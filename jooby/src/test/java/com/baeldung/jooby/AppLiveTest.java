package com.baeldung.jooby;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;

import org.jooby.test.JoobyRule;
import org.junit.ClassRule;
import org.junit.Test;

public class AppLiveTest {

	@ClassRule
	public static JoobyRule app = new JoobyRule(new App());

	@Test
	public void given_defaultUrl_expect_fixedString() {
		get("/").then().assertThat().body(equalTo("Hello World!")).statusCode(200)
				.contentType("text/html;charset=UTF-8");
	}

}
