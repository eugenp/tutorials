package com.baeldung.java.io.jsch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;

@Testcontainers
public class SftpLiveTest {

    private static final int SFTP_PORT = 22;

    private static final String USERNAME = "foo";
    private static final String PASSWORD = "pass";

    // mobilistics/sftp is a Docker container for an SFTP server that reliably allows file upload and download.
    // Unfortunately, atmoz/sftp intermittently doesn't allow writing to the server.
    @Container
    public static GenericContainer<?> sftpContainer = new GenericContainer<>("mobilistics/sftp:latest")
        .withExposedPorts(SFTP_PORT)
        .withEnv("USER", USERNAME)
        .withEnv("PASS", PASSWORD)
        .withCopyFileToContainer(
            MountableFile.forHostPath(Paths.get("src/main/resources/input.txt")),
            "/data/incoming/input.txt"
        );

    @Test
    void whenGettingTheCurrentDirectory_thenTheCorrectValueIsReturned() throws Exception {
        JSch jSch = new JSch();

        Session session = jSch.getSession(USERNAME, sftpContainer.getHost(), sftpContainer.getMappedPort(SFTP_PORT));

        session.setUserInfo(new SftpUserInfo());

        session.connect();

        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftp = (ChannelSftp) channel;

        assertEquals("/", sftp.pwd());
        assertTrue(sftp.lpwd().endsWith("/libraries-io"));
    }

    @Test
    void whenListingRemoteFiles_thenTheCorrectFilesAreReturned() throws Exception {
        JSch jSch = new JSch();

        Session session = jSch.getSession(USERNAME, sftpContainer.getHost(), sftpContainer.getMappedPort(SFTP_PORT));

        session.setUserInfo(new SftpUserInfo());

        session.connect();

        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftp = (ChannelSftp) channel;

        List<ChannelSftp.LsEntry> remoteLs = sftp.ls(".");
        assertEquals(3, remoteLs.size()); // ., ../ and incoming

        ChannelSftp.LsEntry incoming = remoteLs.stream()
            .filter(file -> file.getFilename()
                .equals("incoming"))
            .findFirst()
            .get();

        assertEquals("incoming", incoming.getFilename());

        SftpATTRS attrs = incoming.getAttrs();
        assertEquals("drwxr-xr-x", attrs.getPermissionsString());
    }

    @Test
    void whenGettingFileStatus_thenTheCorrectValueIsReturned() throws Exception {
        JSch jSch = new JSch();

        Session session = jSch.getSession(USERNAME, sftpContainer.getHost(), sftpContainer.getMappedPort(SFTP_PORT));

        session.setUserInfo(new SftpUserInfo());

        session.connect();

        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftp = (ChannelSftp) channel;

        SftpATTRS attrs = sftp.stat("incoming");
        assertEquals("drwxr-xr-x", attrs.getPermissionsString());
    }

    @Test
    void whenPuttingFiles_thenTheFilesAreCreatedOnTheServer() throws Exception {
        JSch jSch = new JSch();

        Session session = jSch.getSession(USERNAME, sftpContainer.getHost(), sftpContainer.getMappedPort(SFTP_PORT));

        session.setUserInfo(new SftpUserInfo());

        session.connect();

        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftp = (ChannelSftp) channel;

        sftp.cd("incoming");

        try (OutputStream outputStream = sftp.put("os.txt")) {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStreamWriter.write("Hello, world!");
            outputStreamWriter.close();
        }

        // Check the files exist
        sftp.stat("os.txt");
    }

    @Test
    void whenGettingAFile_thenTheFileContentsAreRetrieved() throws Exception {
        JSch jSch = new JSch();

        Session session = jSch.getSession(USERNAME, sftpContainer.getHost(), sftpContainer.getMappedPort(SFTP_PORT));

        session.setUserInfo(new SftpUserInfo());

        session.connect();

        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftp = (ChannelSftp) channel;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        sftp.get("incoming/input.txt", baos);

        assertEquals("This is a sample text content", new String(baos.toByteArray()));
    }

    private static class SftpUserInfo extends BaseUserInfo {
        @Override
        public String getPassword() {
            return PASSWORD;
        }

        @Override
        public boolean promptPassword(String message) {
            return true;
        }

        @Override
        public boolean promptYesNo(String message) {
            if (message.startsWith("The authenticity of host")) {
                return true;
            }

            return false;
        }
    }
}
