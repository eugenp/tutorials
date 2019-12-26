package org.baeldung.java.io.remote;

import java.io.IOException;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.VFS;
import org.junit.Test;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

public class SftpFileTransferLiveTest {

    private String remoteHost = "HOST_NAME_HERE";
    private String username = "USERNAME_HERE";
    private String password = "PASSWORD_HERE";
    private String localFile = "src/main/resources/input.txt";
    private String remoteFile = "welcome.txt";
    private String localDir = "src/main/resources/";
    private String remoteDir = "remote_sftp_test/";
    private String knownHostsFileLoc = "/Users/USERNAME/known_hosts_sample";

    @Test
    public void whenUploadFileUsingJsch_thenSuccess() throws JSchException, SftpException {
        ChannelSftp channelSftp = setupJsch();
        channelSftp.connect();
        channelSftp.put(localFile, remoteDir + "jschFile.txt");
        channelSftp.exit();
    }

    @Test
    public void whenDownloadFileUsingJsch_thenSuccess() throws JSchException, SftpException {
        ChannelSftp channelSftp = setupJsch();
        channelSftp.connect();
        channelSftp.get(remoteFile, localDir + "jschFile.txt");
        channelSftp.exit();
    }

    @Test
    public void whenUploadFileUsingSshj_thenSuccess() throws IOException {
        SSHClient sshClient = setupSshj();
        SFTPClient sftpClient = sshClient.newSFTPClient();
        sftpClient.put(localFile, remoteDir + "sshjFile.txt");
        sftpClient.close();
        sshClient.disconnect();
    }

    @Test
    public void whenDownloadFileUsingSshj_thenSuccess() throws IOException {
        SSHClient sshClient = setupSshj();
        SFTPClient sftpClient = sshClient.newSFTPClient();
        sftpClient.get(remoteFile, localDir + "sshjFile.txt");
        sftpClient.close();
        sshClient.disconnect();
    }

    @Test
    public void whenUploadFileUsingApacheVfs_thenSuccess() throws IOException {
        FileSystemManager manager = VFS.getManager();
        FileObject local = manager.resolveFile(System.getProperty("user.dir") + "/" + localFile);
        FileObject remote = manager.resolveFile("sftp://" + username + ":" + password + "@" + remoteHost + "/" + remoteDir + "vfsFile.txt");
        remote.copyFrom(local, Selectors.SELECT_SELF);
        local.close();
        remote.close();
    }

    @Test
    public void whenDownloadFileUsingApacheVfs_thenSuccess() throws IOException {
        FileSystemManager manager = VFS.getManager();
        FileObject local = manager.resolveFile(System.getProperty("user.dir") + "/" + localDir + "vfsFile.txt");
        FileObject remote = manager.resolveFile("sftp://" + username + ":" + password + "@" + remoteHost + "/" + remoteFile);
        local.copyFrom(remote, Selectors.SELECT_SELF);
        local.close();
        remote.close();
    }

    // ====== ssh-keyscan -H -t rsa remoteHost >> known_hosts_sample

    private ChannelSftp setupJsch() throws JSchException {
        JSch jsch = new JSch();
        jsch.setKnownHosts(knownHostsFileLoc);
        Session jschSession = jsch.getSession(username, remoteHost);
        jschSession.setPassword(password);
        //jschSession.setConfig("StrictHostKeyChecking", "no");
        jschSession.connect();
        return (ChannelSftp) jschSession.openChannel("sftp");
    }

    private SSHClient setupSshj() throws IOException {
        SSHClient client = new SSHClient();
        client.addHostKeyVerifier(new PromiscuousVerifier());
        client.connect(remoteHost);
        client.authPassword(username, password);
        return client;
    }

}
