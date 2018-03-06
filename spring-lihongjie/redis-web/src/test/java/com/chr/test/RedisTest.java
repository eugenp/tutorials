package com.chr.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chr.domain.User;
import com.chr.service.impl.UserOperationsServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/spring-context.xml",
		"classpath:config/redis-context.xml" })
public class RedisTest {

	@Autowired
	private UserOperationsServiceImpl userops;

	@Test
	public void Test1() {
		User user = new User("1", "chenhaoran", "admin");
		userops.add(user);
		User user1 = userops.getUser(user.getId());
		System.out.println(user1);
		System.out.println(user1.getId() + user1.getName()
				+ user1.getPassword());
	}
}
