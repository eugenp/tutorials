package com.baeldung.socket;

import static org.junit.Assert.*;

import org.junit.Test;

public class EchoMultiTest {
	@Test
	public void givenClient1_whenServerResponds_thenCorrect() {
		EchoClient client1 = new EchoClient();
		client1.startConnection("127.0.0.1", 5555);
		String msg1 = client1.sendMessage("hello");
		String msg2 = client1.sendMessage("world");
		String terminate = client1.sendMessage(".");
		assertEquals(msg1, "hello");
		assertEquals(msg2, "world");
		assertEquals(terminate, "bye");
	}

	@Test
	public void givenClient2_whenServerResponds_thenCorrect() {
		EchoClient client1 = new EchoClient();
		client1.startConnection("127.0.0.1", 5555);
		String msg1 = client1.sendMessage("hello");
		String msg2 = client1.sendMessage("world");
		String terminate = client1.sendMessage(".");
		assertEquals(msg1, "hello");
		assertEquals(msg2, "world");
		assertEquals(terminate, "bye");
	}

	@Test
	public void givenClient3_whenServerResponds_thenCorrect() {
		EchoClient client1 = new EchoClient();
		client1.startConnection("127.0.0.1", 5555);
		String msg1 = client1.sendMessage("hello");
		String msg2 = client1.sendMessage("world");
		String terminate = client1.sendMessage(".");
		assertEquals(msg1, "hello");
		assertEquals(msg2, "world");
		assertEquals(terminate, "bye");
	}

}
