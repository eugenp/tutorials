package com.baeldung.socketexception;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

import org.junit.BeforeClass;
import org.junit.Test;

public class SocketExceptionHandlingUnitTest {
    private static final int PORT = 6699;

    @BeforeClass
    public static void runServer() throws IOException, InterruptedException {
        Executors.newSingleThreadExecutor()
          .submit(() -> new SocketServer().start(PORT));
        Thread.sleep(100);
    }

    @Test
    public void givenRunningServer_whenConnectToClosedSocket_thenHandleException() throws IOException {
        SocketClient client = new SocketClient();
        client.startConnection("127.0.0.1", PORT);
        try {
            client.sendMessage("hi");
            client.sendMessage("hi again");
        } catch (SocketException e) {
            client.stopConnection();
        }
    }

    @Test
    public void givenRunningServer_whenConnectToSocket_thenHandleConnectionResetException() throws IOException {
        // Enable multiple SSL/TLS protocols
        String[] enabledProtocols = { "TLSv1.2", "TLSv1.3", "TLSv1.1", "TLSv1", "SSLv3", "SSLv3" };
        SSLServerSocketFactory mockFactory = mock(SSLServerSocketFactory.class);
        SSLServerSocket mockServerSocket = mock(SSLServerSocket.class);

        // Set up the mock factory to return the mock server socket
        when(mockFactory.createServerSocket(PORT)).thenReturn(mockServerSocket);

        // Call the method being tested
        SslServer.createSSLSocketWithEnabledProtocols(mockFactory, PORT, enabledProtocols);

        // Verify that setEnabledProtocols and close were called
        verify(mockServerSocket).setEnabledProtocols(enabledProtocols);
        verify(mockServerSocket).close();
    }

}
