package com.baeldung.listfilesremoteserver;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.RemoteResourceInfo;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class RemoteServerSshj {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteServerSshj.class);
    private static final String HOST = "HOST_NAME";
    private static final String USER = "USERNAME";
    private static final String PRIVATE_KEY = "PRIVATE_KEY";
    private static final String REMOTE_DIR = "REMOTE_DIR";

    public static void listFileWithSshj() throws IOException {
        try (SSHClient sshClient = new SSHClient()) {
            sshClient.addHostKeyVerifier(new PromiscuousVerifier());
            sshClient.connect(HOST);
            sshClient.authPublickey(USER, PRIVATE_KEY);

            try (SFTPClient sftpClient = sshClient.newSFTPClient()) {
                List<RemoteResourceInfo> files = sftpClient.ls(REMOTE_DIR);
                for (RemoteResourceInfo file : files) {
                    LOGGER.info("Filename: " + file.getName());
                    LOGGER.info("Permissions: " + file.getAttributes()
                        .getPermissions());
                    LOGGER.info("Last Modification Time: " + file.getAttributes()
                        .getMtime());
                }
            }
        }
    }
}