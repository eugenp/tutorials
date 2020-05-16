package com.baeldung.rss;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Test for {@link RssFeedApplication}.
 * 
 * @author Donato Rimenti
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RssFeedApplication.class)
public class RssFeedUnitTest {

	/**
	 * Application context.
	 */
	@Autowired
	private WebApplicationContext context;

	/**
	 * Mock to perform tests on Spring Web Controller.
	 */
	private MockMvc mvc;

	/**
	 * Sets the test up.
	 */
	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	/**
	 * Calls the RSS feed endpoint and checks that the result matches an
	 * expected one.
	 * 
	 * @throws Exception
	 */
	@Test
	public void givenRssFeed_whenComparedWithExisting_thenEquals() throws Exception {
		// The expected response.
		String expectedResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><rss version=\"2.0\"> <channel> <title>Baeldung RSS Feed</title> <link>http://www.baeldung.com</link> <description>Learn how to program in Java</description> <item> <title>JUnit 5 @Test Annotation</title> <link>http://www.baeldung.com/junit-5-test-annotation</link> <pubDate>Tue, 19 Dec 2017 00:00:00 GMT</pubDate> <author>donatohan.rimenti@gmail.com</author> </item> <item> <title>Creating and Configuring Jetty 9 Server in Java</title> <link>http://www.baeldung.com/jetty-java-programmatic</link> <pubDate>Tue, 23 Jan 2018 00:00:00 GMT</pubDate> <author>donatohan.rimenti@gmail.com</author> </item> <item> <title>Flyweight Pattern in Java</title> <link>http://www.baeldung.com/java-flyweight</link> <pubDate>Thu, 01 Feb 2018 00:00:00 GMT</pubDate> <author>donatohan.rimenti@gmail.com</author> </item> <item> <title>Multi-Swarm Optimization Algorithm in Java</title> <link>http://www.baeldung.com/java-multi-swarm-algorithm</link> <pubDate>Fri, 09 Mar 2018 00:00:00 GMT</pubDate> <author>donatohan.rimenti@gmail.com</author> </item> <item> <title>A Simple Tagging Implementation with MongoDB</title> <link>http://www.baeldung.com/mongodb-tagging</link> <pubDate>Tue, 27 Mar 2018 00:00:00 GMT</pubDate> <author>donatohan.rimenti@gmail.com</author> </item> <item> <title>Double-Checked Locking with Singleton</title> <link>http://www.baeldung.com/java-singleton-double-checked-locking</link> <pubDate>Mon, 23 Apr 2018 00:00:00 GMT</pubDate> <author>donatohan.rimenti@gmail.com</author> </item> <item> <title>Introduction to Dagger 2</title> <link>http://www.baeldung.com/dagger-2</link> <pubDate>Sat, 30 Jun 2018 00:00:00 GMT</pubDate> <author>donatohan.rimenti@gmail.com</author> </item> </channel></rss>";

		// Performs a post against the RSS feed endpoint and checks that the
		// result is equals to the expected one.
		mvc.perform(MockMvcRequestBuilders.get("/rss")).andExpect(status().isOk())
				.andExpect(content().xml(expectedResult));
	}

}
