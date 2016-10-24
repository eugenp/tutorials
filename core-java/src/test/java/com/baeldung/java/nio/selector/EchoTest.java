package com.baeldung.java.nio.selector;

import static org.junit.Assert.assertEquals;

<<<<<<< HEAD
=======
import java.io.IOException;

>>>>>>> 92d320cd9a02af33413a1c229c3234587bae5db1
import org.junit.Test;

public class EchoTest {

    @Test
<<<<<<< HEAD
    public void givenClient_whenServerEchosMessage_thenCorrect() {
=======
    public void givenClient_whenServerEchosMessage_thenCorrect() throws IOException, InterruptedException {
        Process process = EchoServer.start();
>>>>>>> 92d320cd9a02af33413a1c229c3234587bae5db1
        EchoClient client = EchoClient.start();
        String resp1 = client.sendMessage("hello");
        String resp2 = client.sendMessage("world");
        assertEquals("hello", resp1);
        assertEquals("world", resp2);
<<<<<<< HEAD
    }

=======
        process.destroy();
    }
>>>>>>> 92d320cd9a02af33413a1c229c3234587bae5db1
}
