package com.baeldung.sshj;

import com.google.common.io.Resources;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.LocalPortForwarder;
import net.schmizz.sshj.connection.channel.forwarded.RemotePortForwarder;
import net.schmizz.sshj.sftp.FileAttributes;
import net.schmizz.sshj.sftp.SFTPClient;
import org.apache.sshd.server.SshServer;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SSHJLibExampleUnitTest {

    private static final String rebex_username = "demo";
    private static final String rebex_password = "password";
    private static final String rebex_host = "test.rebex.net";
    private static final int rebex_port = 22;

    private static final int PORT = 2222;
    private static final String HOST = "localhost";
    private static final String USER = "testuser";
    private static final String PASSWORD = "testpassword";
    private static final String SCP_FILE_NAME = "test_file_SCP.txt";
    private static final String SFTP_FILE_NAME = "test_file_SFTP.txt";
    
    private static SSHClient sshClient;

    private static SSHClient rebexSshClient;

    private static SFTPClient sftpClient;

    private static SshServer sshdServer;

    private static String downLoadPath;

    private static String privateKeyPath;

    @BeforeClass
    public static void setUp() throws Exception {
        Path resourcesPath = Paths.get("src", "main", "resources");

        downLoadPath = resourcesPath.resolve("downloads")
            .toAbsolutePath()
            .toString();

        privateKeyPath = resourcesPath.resolve("PrivateKeys")
            .toAbsolutePath()
            .toString();
        FileUtils.cleanDirectory(new File(downLoadPath));
        FileUtils.cleanDirectory(new File(resourcesPath.resolve("home/upload")
            .toAbsolutePath()
            .toString()));

        sshdServer = SSHServerSetup.setupServer();
        sshClient = SSHJAppDemo.loginSftp(HOST, PORT, USER, PASSWORD);
        rebexSshClient = SSHJAppDemo.loginSftp(rebex_host, rebex_port, rebex_username, rebex_password);
        sftpClient = sshClient.newSFTPClient();
    }

    @AfterClass
    public static void shutdownServer() throws Exception {
        FileUtils.cleanDirectory(new File(downLoadPath));
        if (sshClient != null && sshClient.isConnected()) {
            sshClient.disconnect();
        }
        sshdServer.stop();
        sftpClient.close();
    }
    
    @Test
    public void whenUserPassAuth_thenConnected() throws IOException {
        assertEquals(true, sshClient.isConnected(), "Server connected via ssh login using user and passowrd");
    }
    
    @Test
    public void whenUserPubKeyAuth_thenConnected() throws IOException {
        SSHClient pubSSHClient = SSHJAppDemo.loginPubKey(HOST, USER, privateKeyPath, PORT);
        assertEquals(true, pubSSHClient.isConnected(), "Server connected via ssh login using pub key");
    }
    
    @Test
    public void whenCmdExecuted_thenResultReturned() throws IOException {
        String response = SSHJAppDemo.executeCommand(rebexSshClient);
        assertEquals("success", response, "command executed on the server successfully");
    }
    
    @Test
    public void whenFileSCPUploadDownload_thenFileUploadedandDownloaded() throws IOException {
        String scpfilePath = Resources.getResource(SCP_FILE_NAME)
            .getPath();
        SSHJAppDemo.scpUpload(sshClient, scpfilePath);
        FileAttributes stat = null;
        try {
        stat = sftpClient.stat("/upload/" + SCP_FILE_NAME);
        } catch (Exception e) {
        }
        assertNotNull(stat, "SCP file upload successful");

        SSHJAppDemo.scpDownLoad(sshClient, downLoadPath, SCP_FILE_NAME);
        assertEquals(true, Files.exists(Path.of(downLoadPath + File.separator + SCP_FILE_NAME)), "SCP file download successful");
    }

    @Test
    public void whenFileSFTPUploadDownload_thenFileUploadedandDownloaded() throws IOException {
        String sftpfilePath = Resources.getResource(SFTP_FILE_NAME)
            .getPath();
        SSHJAppDemo.SFTPUpload(sshClient, sftpfilePath);
        FileAttributes stat = null;
        try {
            stat = sftpClient.stat("/upload/" + SFTP_FILE_NAME);
        } catch (Exception e) {
        }
        assertNotNull(stat, "SFTP file upload successful");
        
        SSHJAppDemo.SFTPDownLoad(sshClient, downLoadPath, SFTP_FILE_NAME);
        assertEquals(true, Files.exists(Path.of(downLoadPath + File.separator + SFTP_FILE_NAME)), "SFTP file download successful");
    }

    @Test
    public void whenLocalPortForward_thenLocalPortForwarded() throws IOException, InterruptedException {
        LocalPortForwarder lpf = SSHJAppDemo.localPortForwarding(sshClient);
        Thread.sleep(2000);//added sleep so thread in above method is executed and we can check local port forwarding
        assertEquals(true, lpf.isRunning(), "Local port forwarding should be successful");
    }
    
    @Test
    public void whenRemotePortForward_thenRemotePortForwarded() throws IOException, InterruptedException {
        RemotePortForwarder rpf = SSHJAppDemo.remotePortForwarding(sshClient);
        Thread.sleep(2000);//added sleep so thread in above method is executed and we can check remote port forwarding
        assertEquals(1, rpf.getActiveForwards()
            .size(), "Remote port forwarding should be successful");
    }
}
