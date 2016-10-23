package com.baeldung.socket;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

public class EchoTest {
    private static final Integer PORT = 4444;

    @BeforeClass
    public static void start() throws InterruptedException {
        Executors.newSingleThreadExecutor().submit(() -> new EchoServer().start(PORT));
        Thread.sleep(500);
    }

	private EchoClient client = new EchoClient();

	@Before
	public void init() {
		client.startConnection("127.0.0.1", PORT);
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
