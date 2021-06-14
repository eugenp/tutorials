package com.baeldung.error.examples;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestNoClassDefError {
    @Test
	public void when_run_asset_equals() {
		App newApp = new App();
		int num1 =1; int num2 = 2;

		assertEquals("3",newApp.sum(num1, num2));
	}

}
