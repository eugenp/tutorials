package com.baeldung.dependencyinjectiontypes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baeldung.dependencyinjections.MailBox;
import com.baeldung.dependencyinjections.TelephoneDirectory;

import junit.framework.TestCase;

/**
 * Unit test for SpringDIDemoTest.
 */
public class SpringDIDemoTest extends TestCase {
	private ApplicationContext context = null;

	public SpringDIDemoTest(String testName) {
		super(testName);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		context = new ClassPathXmlApplicationContext("diContext.xml");
	}

	/**
	 * Constructor Based Application Context
	 */
	public void testWhenOpenMailBox_ThenGetMessage() {
		MailBox mailBox = (MailBox) context.getBean("mailBox");
		System.out.println(mailBox.getMessage());
		assertTrue(true);
	}

	/**
	 * Setter/Getter Based Application Context
	 */
	public void testWhenReadDirectory_ThenDisplayPhoneDetails() {
		TelephoneDirectory telephoneDirectory = (TelephoneDirectory) context.getBean("telephoneDirectory");
		telephoneDirectory.displayPhoneDetails();
		assertTrue(true);
	}
}
