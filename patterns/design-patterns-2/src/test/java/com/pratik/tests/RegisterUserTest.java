/**
 * 
 */
package com.pratik.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.pratik.userapp.AwsNotificationService;
import com.pratik.userapp.NotificationService;
import com.pratik.userapp.OracleUserRepository;
import com.pratik.userapp.RegisterUserService;
import com.pratik.userapp.User;
import com.pratik.userapp.UserRepository;

/**
 * @author Pratik Das
 *
 */
public class RegisterUserTest {

	UserRepository userRepository;
	NotificationService notificationService;
	User user;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		userRepository = new OracleUserRepository();
		notificationService = new AwsNotificationService();
		user = new User();
		user.setEmail("pra@gmail.com");
		user.setName("pra");
	}

	@Test
	public void testRegister() {
		
		RegisterUserService registerUserService = new RegisterUserService(userRepository, notificationService);
		
		boolean result = registerUserService.register(user);
		assertTrue("User registered", result);
	}

}
