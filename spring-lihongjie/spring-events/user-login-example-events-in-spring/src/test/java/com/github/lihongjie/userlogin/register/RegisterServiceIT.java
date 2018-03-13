package com.github.lihongjie.userlogin.register;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-config-register.xml" })
public class RegisterServiceIT {
	
	@Autowired
	private RegisterService registerService;

	@Test
	public void testRegister() {
		registerService.register("long", "123");
	}
}
