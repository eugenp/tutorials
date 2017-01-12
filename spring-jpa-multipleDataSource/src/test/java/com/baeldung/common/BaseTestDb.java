package com.baeldung.common;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.entity.primarydb.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-config/application/applicationContext.xml" })
public class BaseTestDb {

	public BaseTestDb() {
	}

	public User createUser() {
		User user = new User();
		user.setUserName("ABC");
		user.setUserDetails("address - mobile");
		return user;
	}
}
