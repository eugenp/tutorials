package com.baeldung.socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

public class EchoMultiTest {

	{
		Executors.newSingleThreadExecutor().submit(() -> new EchoMultiServer().start(5555));
	}


	@Test
	public void givenClient1_whenServerResponds_thenCorrect() {
		EchoClient client = new EchoClient();
		client.startConnection("127.0.0.1", 5555);
		String msg1 = client.sendMessage("hello");
		String msg2 = client.sendMessage("world");
		String terminate = client.sendMessage(".");
		assertEquals(msg1, "hello");
		assertEquals(msg2, "world");
		assertEquals(terminate, "bye");
		client.stopConnection();
	}

	@Test
	public void givenClient2_whenServerResponds_thenCorrect() {
		EchoClient client = new EchoClient();
		client.startConnection("127.0.0.1", 5555);
		String msg1 = client.sendMessage("hello");
		String msg2 = client.sendMessage("world");
		String terminate = client.sendMessage(".");
		assertEquals(msg1, "hello");
		assertEquals(msg2, "world");
		assertEquals(terminate, "bye");
		client.stopConnection();
	}

	@Test
	public void givenClient3_whenServerResponds_thenCorrect() {
		EchoClient client = new EchoClient();
		client.startConnection("127.0.0.1", 5555);
		String msg1 = client.sendMessage("hello");
		String msg2 = client.sendMessage("world");
		String terminate = client.sendMessage(".");
		assertEquals(msg1, "hello");
		assertEquals(msg2, "world");
		assertEquals(terminate, "bye");
		client.stopConnection();
	}

}
