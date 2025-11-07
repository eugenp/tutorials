package com.baeldung.java.io.jsch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.JSchUnknownHostKeyException;
import com.jcraft.jsch.Session;

@Testcontainers
class ConnectLiveTest {
    private static final int SFTP_PORT = 22;

    private static final String USERNAME = "baeldung";
    private static final String PASSWORD = "test";
    private static final String DIRECTORY = "upload";

    // atmoz/sftp is a Docker container for an SFTP server that can easily support password and key authentication.
    @Container
    public static GenericContainer<?> sftpContainer = new GenericContainer<>("atmoz/sftp:latest")
        .withExposedPorts(SFTP_PORT)
        .withCommand(USERNAME + ":" + PASSWORD + ":::" + DIRECTORY)
        .withCopyFileToContainer(
            MountableFile.forHostPath(Paths.get("src/test/resources/com/baeldung/java/io/jsch/nopassphrase/id_rsa.pub")),
            "/home/" + USERNAME + "/.ssh/keys/id_rsa.pub"
        )
        .withCopyFileToContainer(
            MountableFile.forHostPath(Paths.get("src/test/resources/com/baeldung/java/io/jsch/passphrase/id_rsa.pub")),
            "/home/" + USERNAME + "/.ssh/keys/id_rsa2.pub"
        );

    @Test
    void whenTheHostKeyIsUnknown_thenTheConnectionFails() throws Exception {
        JSch jSch = new JSch();

        Session session = jSch.getSession(USERNAME, sftpContainer.getHost(), sftpContainer.getMappedPort(SFTP_PORT));

        session.setUserInfo(new BaseUserInfo() {});

        assertThrows(JSchUnknownHostKeyException.class, () -> session.connect());
    }

    @Test
    void whenNoAuthenticationIsProvided_thenTheConnectionFails() throws Exception {
        JSch jSch = new JSch();

        Session session = jSch.getSession(USERNAME, sftpContainer.getHost(), sftpContainer.getMappedPort(SFTP_PORT));

        session.setUserInfo(new BaseUserInfo() {

            @Override
            public boolean promptYesNo(String message) {
                if (message.startsWith("The authenticity of host")) {
                    return true;
                }

                return false;
            }
        });

        JSchException exception = assertThrows(JSchException.class, () -> session.connect());
        assertEquals("Auth cancel for methods 'publickey,password,keyboard-interactive'", exception.getMessage());
    }

    @Test
    void whenPasswordAuthIsProvided_thenTheConnectionSucceeds() throws Exception {
        JSch jSch = new JSch();

        Session session = jSch.getSession(USERNAME, sftpContainer.getHost(), sftpContainer.getMappedPort(SFTP_PORT));

        session.setUserInfo(new BaseUserInfo() {
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
        });

        session.connect();
    }

    @Test
    void givenAnOpenSSHKey_whenKeyAuthIsProvided_thenTheConnectionSucceeds() throws Exception {
        JSch jSch = new JSch();
        jSch.addIdentity("src/test/resources/com/baeldung/java/io/jsch/nopassphrase/id_rsa");

        Session session = jSch.getSession(USERNAME, sftpContainer.getHost(), sftpContainer.getMappedPort(SFTP_PORT));

        session.setUserInfo(new BaseUserInfo() {
            @Override
            public boolean promptYesNo(String message) {
                if (message.startsWith("The authenticity of host")) {
                    return true;
                }

                return false;
            }
        });

        session.connect();
    }

    @Test
    void givenAPuttyKey_whenKeyAuthIsProvided_thenTheConnectionSucceeds() throws Exception {
        JSch jSch = new JSch();
        jSch.addIdentity("src/test/resources/com/baeldung/java/io/jsch/nopassphrase/id_rsa.ppk");

        Session session = jSch.getSession(USERNAME, sftpContainer.getHost(), sftpContainer.getMappedPort(SFTP_PORT));

        session.setUserInfo(new BaseUserInfo() {
            @Override
            public boolean promptYesNo(String message) {
                if (message.startsWith("The authenticity of host")) {
                    return true;
                }

                return false;
            }
        });

        session.connect();
    }

    @Test
    void givenAnOpenSSHKeyWithPassphrase_whenKeyAuthIsProvided_thenTheConnectionSucceeds() throws Exception {
        JSch jSch = new JSch();
        jSch.addIdentity("src/test/resources/com/baeldung/java/io/jsch/passphrase/id_rsa");

        Session session = jSch.getSession(USERNAME, sftpContainer.getHost(), sftpContainer.getMappedPort(SFTP_PORT));

        session.setUserInfo(new BaseUserInfo() {
            @Override
            public String getPassphrase() {
                return "te5tPa55word";
            }

            @Override
            public boolean promptPassphrase(String message) {
                return true;
            }

            @Override
            public boolean promptYesNo(String message) {
                if (message.startsWith("The authenticity of host")) {
                    return true;
                }

                return false;
            }
        });

        session.connect();
    }
}
