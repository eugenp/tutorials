package com.baeldung.ssh.apachesshd;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.channel.Channel;

public class SshdDemo {

    public static void main(String[] args) throws IOException, InterruptedException {
        String username = "demo";
        String password = "password";
        String host = "test.rebex.net";
        int port = 22;
        long defaultTimeoutSeconds = 10l;
        String command = "ls\n";
        listFolderStructure(username, password, host, port, defaultTimeoutSeconds, command);
    }

    public static void listFolderStructure(String username, String password, String host, int port, long defaultTimeoutSeconds, String command) throws IOException {
        SshClient client = SshClient.setUpDefaultClient();
        client.start();
        try (ClientSession session = client.connect(username, host, port)
            .verify(defaultTimeoutSeconds, TimeUnit.SECONDS)
            .getSession()) {
            session.addPasswordIdentity(password);
            session.auth()
                .verify(5L, TimeUnit.SECONDS);
            try (ByteArrayOutputStream out = new ByteArrayOutputStream(); ClientChannel channel = session.createChannel(Channel.CHANNEL_SHELL)) {
                channel.setOut(out);
                try {
                    channel.open()
                        .verify(defaultTimeoutSeconds, TimeUnit.SECONDS);
                    try (OutputStream pipedIn = channel.getInvertedIn()) {
                        pipedIn.write(command.getBytes());
                        pipedIn.flush();
                    }
                    channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), TimeUnit.SECONDS.toMillis(defaultTimeoutSeconds));
                    String finalString = new String(out.toByteArray());
                    System.out.println("out: " + finalString);
                } finally {
                    channel.close(false);
                }
            }
        } finally {
            client.stop();
        }
    }

}
