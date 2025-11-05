package com.baeldung.listfilesremoteserver;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.sftp.client.SftpClient;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import static com.baeldung.listfilesremoteserver.RemoteServerJsch.createSession;
import static com.baeldung.listfilesremoteserver.RemoteServerJsch.setUpJsch;
import static com.baeldung.listfilesremoteserver.RemoteServerJsch.createSftpChannel;
import static com.baeldung.listfilesremoteserver.RemoteServerJsch.listFiles;
import static com.baeldung.listfilesremoteserver.RemoteServerJsch.printFiles;
import static com.baeldung.listfilesremoteserver.RemoteServerJsch.disconnect;
import static com.baeldung.listfilesremoteserver.RemoteServerSshj.listFileWithSshj;
import static com.baeldung.listfilesremoteserver.RemoteServerApacheSshd.connectToServer;
import static com.baeldung.listfilesremoteserver.RemoteServerApacheSshd.setUpSshClient;
import static com.baeldung.listfilesremoteserver.RemoteServerApacheSshd.createSftpClient;
import static com.baeldung.listfilesremoteserver.RemoteServerApacheSshd.listDirectory;
import static com.baeldung.listfilesremoteserver.RemoteServerApacheSshd.printDirectoryEntries;

public class RemoteServerFileListingLiveTest {

    private static final String REMOTE_DIR = "REMOTE_DIR";

    @Test
    public void givenJsch_whenListFiles_thenSuccess() throws JSchException, SftpException {
        JSch jsch = setUpJsch();
        Session session = createSession(jsch);
        ChannelSftp channelSftp = createSftpChannel(session);

        Vector<ChannelSftp.LsEntry> files = listFiles(channelSftp, REMOTE_DIR);
        printFiles(files);
        disconnect(channelSftp, session);
    }

    @Test
    public void givenApacheSshd_whenListFiles_thenSuccess() throws IOException {
        try (SshClient client = setUpSshClient(); ClientSession session = connectToServer(client); SftpClient sftp = createSftpClient(session)) {

            List<SftpClient.DirEntry> entries = listDirectory(sftp, REMOTE_DIR);
            printDirectoryEntries(entries);
        }
    }

    @Test
    public void givenSshj_whenListFiles_thenSuccess() throws IOException {
        listFileWithSshj();
    }

}