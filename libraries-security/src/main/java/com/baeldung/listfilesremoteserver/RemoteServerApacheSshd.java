package com.baeldung.listfilesremoteserver;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.keyverifier.AcceptAllServerKeyVerifier;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.keyprovider.FileKeyPairProvider;
import org.apache.sshd.sftp.client.SftpClient;
import org.apache.sshd.sftp.client.SftpClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RemoteServerApacheSshd {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteServerApacheSshd.class);
    private static final String HOST = "HOST_NAME";
    private static final String USER = "USERNAME";
    private static final String PRIVATE_KEY = "PRIVATE_KEY";
    private static final int PORT = 22;

    public static SshClient setUpSshClient() {
        SshClient client = SshClient.setUpDefaultClient();
        client.start();
        client.setServerKeyVerifier(AcceptAllServerKeyVerifier.INSTANCE);
        return client;
    }

    public static ClientSession connectToServer(SshClient client) throws IOException {
        ClientSession session = client.connect(USER, HOST, PORT)
            .verify(10000)
            .getSession();

        FileKeyPairProvider fileKeyPairProvider = new FileKeyPairProvider(Paths.get(PRIVATE_KEY));
        Iterable<KeyPair> keyPairs = fileKeyPairProvider.loadKeys(null);
        for (KeyPair keyPair : keyPairs) {
            session.addPublicKeyIdentity(keyPair);
        }

        session.auth()
            .verify(10000);
        return session;
    }

    public static SftpClient createSftpClient(ClientSession session) throws IOException {
        SftpClientFactory factory = SftpClientFactory.instance();
        return factory.createSftpClient(session);
    }

    public static List<SftpClient.DirEntry> listDirectory(SftpClient sftp, String remotePath) throws IOException {
        Iterable<SftpClient.DirEntry> entriesIterable = sftp.readDir(remotePath);
        return StreamSupport.stream(entriesIterable.spliterator(), false)
            .collect(Collectors.toList());
    }

    public static void printDirectoryEntries(List<SftpClient.DirEntry> entries) {
        for (SftpClient.DirEntry entry : entries) {
            LOGGER.info(entry.getFilename());
            LOGGER.info(entry.getLongFilename());
        }
    }
}
