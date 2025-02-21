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
        clientContext = manager.createContext(serverName, krb5Oid, (GSSCredential) null, GSSContext.DEFAULT_LIFETIME);
        clientContext.requestMutualAuth(true);
        clientContext.requestConf(true);
        clientContext.requestInteg(true);
    }

    @Test
    public void givenCredential_whenStarted_thenAutenticationWorks() throws SaslException, GSSException {
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

        assertTrue(serverContext.isEstablished());
        assertTrue(clientContext.isEstablished());
    }

    @Test
    public void givenContext_whenStarted_thenSecurityWorks() throws SaslException, GSSException {
        // On the client-side
        byte[] messageBytes = "Baeldung".getBytes();
        MessageProp clientProp = new MessageProp(0, true);
        byte[] clientToken = clientContext.wrap(messageBytes, 0, messageBytes.length, clientProp);
        // sendToServer(clientToken); // This is supposed to be send over the network

        // On the server-side
        MessageProp serverProp = new MessageProp(0, false);
        byte[] bytes = serverContext.unwrap(clientToken, 0, clientToken.length, serverProp);
        String string = new String(bytes);

        assertEquals("Baeldung", string);
    }

    @After
    public void tearDown() throws SaslException, GSSException {
        serverContext.dispose();
        clientContext.dispose();
    }

}
