package com.baeldung.customuserdetails;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertNotNull;

import com.baeldung.customuserdetails.ViewController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringSecurityThymeleafApplicationIntegrationTest {

	@Autowired
    ViewController viewController;
	@Autowired
	WebApplicationContext wac;
	
	@Test
	public void whenConfigured_thenLoadsContext() {
		assertNotNull(viewController);
		assertNotNull(wac);
	}

}
