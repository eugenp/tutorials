package com.baeldung.socket;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EchoTest {
	EchoClient client = null;

	@Before
	public void setup() {
		client = new EchoClient();
		client.startConnection("127.0.0.1", 4444);
	}

	@Test
	public void givenClient_whenServerEchosMessage_thenCorrect() {

		String resp1 = client.sendMessage("hello");
		String resp2 = client.sendMessage("world");
		String resp3 = client.sendMessage("!");
		String resp4 = client.sendMessage(".");
		assertEquals("hello", resp1);
		assertEquals("world", resp2);
		assertEquals("!", resp3);
		assertEquals("good bye", resp4);
	}

	@After
	public void tearDown() {
		client.stopConnection();
	}

}
