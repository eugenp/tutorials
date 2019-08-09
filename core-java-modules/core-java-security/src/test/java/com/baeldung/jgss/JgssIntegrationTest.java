package com.baeldung.jgss;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.security.sasl.SaslException;

import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSManager;
import org.ietf.jgss.GSSName;
import org.ietf.jgss.MessageProp;
import org.ietf.jgss.Oid;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class JgssIntegrationTest {
<<<<<<< HEAD

=======
    
>>>>>>> master
    private static final String SERVER_PRINCIPAL = "HTTP/localhost@EXAMPLE.COM";
    private static final String MECHANISM = "1.2.840.113554.1.2.2";

    GSSContext serverContext;
    GSSContext clientContext;

    @Before
    public void setUp() throws SaslException, GSSException {
        GSSManager manager = GSSManager.getInstance();
        serverContext = manager.createContext((GSSCredential) null);
        String serverPrinciple = SERVER_PRINCIPAL;
        GSSName serverName = manager.createName(serverPrinciple, null);
        Oid krb5Oid = new Oid(MECHANISM);
<<<<<<< HEAD
        clientContext = manager.createContext(serverName, krb5Oid, (GSSCredential) null, GSSContext.DEFAULT_LIFETIME);
=======
        clientContext = manager.createContext(
            serverName, krb5Oid, (GSSCredential) null, GSSContext.DEFAULT_LIFETIME);
>>>>>>> master
        clientContext.requestMutualAuth(true);
        clientContext.requestConf(true);
        clientContext.requestInteg(true);
    }

    @Test
    public void givenCredential_whenStarted_thenAutenticationWorks() throws SaslException, GSSException {
<<<<<<< HEAD
        byte[] serverToken;
        byte[] clientToken;

        // On the client-side
        clientToken = clientContext.initSecContext(new byte[0], 0, 0);
        // sendToServer(clientToken); // This is supposed to be send over the network

        // On the server-side
        serverToken = serverContext.acceptSecContext(clientToken, 0, clientToken.length);
        // sendToClient(serverToken); // This is supposed to be send over the network

        // Back on the client-side
        clientContext.initSecContext(serverToken, 0, serverToken.length);

=======
        byte[] serverToken = new byte[0];
        byte[] clientToken = new byte[0];
        clientToken = clientContext.initSecContext(clientToken, 0, clientToken.length);
        serverToken = clientToken;
        serverToken = serverContext.acceptSecContext(serverToken, 0, serverToken.length);
        clientToken = serverToken;
        clientToken = clientContext.initSecContext(clientToken, 0, clientToken.length);
>>>>>>> master
        assertTrue(serverContext.isEstablished());
        assertTrue(clientContext.isEstablished());
    }

    @Test
    public void givenContext_whenStarted_thenSecurityWorks() throws SaslException, GSSException {
<<<<<<< HEAD
        // On the client-side
        byte[] messageBytes = "Baeldung".getBytes();
        MessageProp clientProp = new MessageProp(0, true);
        byte[] clientToken = clientContext.wrap(messageBytes, 0, messageBytes.length, clientProp);
        // sendToServer(clientToken); // This is supposed to be send over the network

        // On the server-side
        MessageProp serverProp = new MessageProp(0, false);
        byte[] bytes = serverContext.unwrap(clientToken, 0, clientToken.length, serverProp);
        clientContext.verifyMIC(clientToken, 0, clientToken.length, bytes, 0, bytes.length, serverProp);
        String string = new String(bytes);

=======
        byte[] messageBytes = "Baeldung".getBytes();
        MessageProp clientProp = new MessageProp(0, true);
        byte[] clientToken = clientContext.wrap(messageBytes, 0, messageBytes.length, clientProp);
        byte[] serverToken = clientToken;
        MessageProp serverProp = new MessageProp(0, false);
        byte[] bytes = serverContext.unwrap(serverToken, 0, serverToken.length, serverProp);
        clientContext.verifyMIC(serverToken, 0, serverToken.length, bytes, 0, bytes.length, serverProp);
        String string = new String(bytes);
>>>>>>> master
        assertEquals("Baeldung", string);
    }

    @After
    public void tearDown() throws SaslException, GSSException {
        serverContext.dispose();
        clientContext.dispose();
    }

}
