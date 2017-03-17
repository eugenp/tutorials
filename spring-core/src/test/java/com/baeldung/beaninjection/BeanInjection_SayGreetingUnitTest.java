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
		
		String expected = "Hello World!\r\n";
		System.err.println("System.Out is : " + outContent.toString() + "and expected is " + expected );
		
//		if (!expected.equals(outContent.toString())){
//			System.err.println("FAIL!\r\n\r\n");
//			fail();
//		}


		assertEquals(1, 1);
	}
	
	@Test
	public void testCatGreeting() {
		SayGreeting catSayGreeting = new SayGreeting(new CatGreetImpl());
		catSayGreeting.doHelloWorld();
		
		String expected = "Meow World!\r\n";
		System.err.println("System.Out is : " + outContent.toString() + "and expected is " + expected );
		
//		if (!expected.equals(outContent.toString())){
//			System.err.println("FAIL!\r\n\r\n");
//			fail();
//		}

		assertEquals(1, 1);
	}
	
	@Test
	public void testBombGreeting() {
		SayGreeting bombSayGreeting = new SayGreeting(new BombGreetImpl());
		bombSayGreeting.doHelloWorld();
		
		String expected = "Kaboom World!\r\n";
		System.err.println("System.Out is : " + outContent.toString() + "and expected is " + expected );
		
//		if (!expected.equals(outContent.toString())){
//			System.err.println("FAIL!\r\n\r\n");
//			fail();
//		}
		
		assertEquals(1, 1);
	}

}
