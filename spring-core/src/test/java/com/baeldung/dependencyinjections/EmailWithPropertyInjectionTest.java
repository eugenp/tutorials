package com.baeldung.dependencyinjections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.dependencyinjections.model.Email;
import com.baeldung.dependencyinjections.model.EmailWithPropertyInjection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={DemoApplication.class})
@ActiveProfiles("pi")
public class EmailWithPropertyInjectionTest {
	
	@Autowired
	Email email;

	@Test
	public void test() {
		System.out.println(email);
		assertNotNull(email);
		assertEquals(EmailWithPropertyInjection.class, email.getClass());
		assertNotNull(email.getSender());
		assertNotNull(email.getContent());
		assertNotNull(email.getReceiver());
	}

}
