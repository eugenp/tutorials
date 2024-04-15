/**
 * 
 */
package com.baeldung.sshj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.LocalPortForwarder;

public class SSHJLibExampleUnitTest {

    private static final int PORT = 22;
    private static final String HOST = "HOST_NAME";
    private static final String USER = "USER_NAME";
    private static final String PASSWORD = "USER_PASSWORD";

    private SSHClient sshClient;

    @Before
    public void setUp() throws Exception {
        sshClient = SSHJAppDemo.loginSftp(HOST, PORT, USER, PASSWORD);
    }
    

    @After
    public void tearDown() throws IOException {
        if (sshClient != null && sshClient.isConnected()) {
            sshClient.disconnect();
        }
    }
    
    @Test
    public void given_whenUserPassAuth_thenConnected() throws IOException {
        assertEquals(true, sshClient.isConnected(), "Server connected via ssh login using user and passowrd");
    }

    @Test
    public void given_whenUserPubKeyAuth_thenConnected() throws IOException {
        SSHClient pubSSHClient = SSHJAppDemo.loginPubKey(HOST, USER);
        assertEquals(true, pubSSHClient.isConnected(), "Server connected via ssh login using pub key");
    }

    @Test
    public void given_whenPingCmdExecuted_thenResultReturned() throws IOException {
        String response = SSHJAppDemo.executeCommand(sshClient);
        assertEquals("success", response, "commoand executed on the server successfully");
    }
    

    @Test
    public void given_whenFileSCPUpload_thenFileUploaded() throws IOException {
        String response = SSHJAppDemo.scpUpload(sshClient);
        assertEquals("success", response, "SCP file download successful");
    }
    
    @Test
    public void given_whenFileSCPDownloaded_thenFileDownloaded() throws IOException {
        String response = SSHJAppDemo.scpDownLoad(sshClient);
        assertEquals("success", response, "SCP file download successful");
    }
    
    @Test
    public void given_whenFileSFTPUpload_thenFileUploaded() throws IOException {
        String response = SSHJAppDemo.SFTPUpload(sshClient);
        assertEquals("success", response, "SFTP file upload successful");
    }
    
    @Test
    public void given_whenFileSFTPDownload_thenFileUploaded() throws IOException {
        String response = SSHJAppDemo.SFTPDownLoad(sshClient);
        assertEquals("success", response, "SFTP file download successful");
    }
    
    @Test
    public void given_whenLocalPortForward_thenLocalPortForwarded() throws IOException, InterruptedException {
        LocalPortForwarder locForwarder = SSHJAppDemo.localPortForwarding(sshClient);

        assertEquals(true, locForwarder.isRunning(), "Local port forwarding should be successful");
    }
    
    @Test
    public void given_whenRemotePortForward_thenRemotePortForwarded() throws IOException, InterruptedException {
        String response = SSHJAppDemo.remotePortForwarding(sshClient);
        assertEquals("success", response, "Remote port forwarding should be successful");
    }


    @Test
    public void given_whenKeepAlive_thenKeptAlive() throws IOException, InterruptedException {
        String response = SSHJAppDemo.KeepAlive(HOST, USER, PASSWORD);
        assertEquals("success", response, "Keep alive execution should be successful");
    }
}
