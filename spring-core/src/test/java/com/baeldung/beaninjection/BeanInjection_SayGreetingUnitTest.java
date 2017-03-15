package com.baeldung.beaninjection;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BeanInjection_SayGreetingUnitTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Before
	public void setUp() throws Exception {
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void tearDown() throws Exception {
		System.setOut(null);
	}

	@Test
	public void testHumanGreeting() {
		SayGreeting humanSayGreeting = new SayGreeting(new GreetImpl());
		humanSayGreeting.doHelloWorld();
		assertEquals("Hello World!\r\n", outContent.toString());
	}
	
	@Test
	public void testCatGreeting() {
		SayGreeting catSayGreeting = new SayGreeting(new CatGreetImpl());
		catSayGreeting.doHelloWorld();
		assertEquals("Meow World!\r\n", outContent.toString());
	}
	
	@Test
	public void testBombGreeting() {
		SayGreeting bombSayGreeting = new SayGreeting(new BombGreetImpl());
		bombSayGreeting.doHelloWorld();
		assertEquals("Kaboom World!\r\n", outContent.toString());
	}

}
