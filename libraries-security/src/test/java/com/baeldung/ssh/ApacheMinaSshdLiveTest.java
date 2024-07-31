package com.baeldung.ssh;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.baeldung.ssh.apachesshd.SshdDemo;

public class ApacheMinaSshdLiveTest {

    @Test
    public void givenValidCredentials_whenConnectionIsEstablished_thenServerReturnsResponse() throws Exception {
        String username = "demo";
        String password = "password";
        String host = "test.rebex.net";
        int port = 22;
        long defaultTimeoutSeconds = 10l;
        String command = "ls\n";
        String responseString = SshdDemo.listFolderStructure(username, password, host, port, defaultTimeoutSeconds, command);
        
        assertNotNull(responseString);
    }

    @Test(expected = Exception.class)
    public void givenInvalidCredentials_whenConnectionAttemptIsMade_thenServerReturnsErrorResponse() throws Exception {
        String username = "invalidUsername";
        String password = "password";
        String host = "test.rebex.net";
        int port = 22;
        long defaultTimeoutSeconds = 10l;
        String command = "ls\n";
        String responseString = SshdDemo.listFolderStructure(username, password, host, port, defaultTimeoutSeconds, command);
        
        assertNull(responseString);
    }

}
