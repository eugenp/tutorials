/**
 * 
 */
package com.baeldung.sshj;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import net.schmizz.keepalive.KeepAliveProvider;
import net.schmizz.sshj.DefaultConfig;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.LocalPortForwarder;
import net.schmizz.sshj.connection.channel.direct.Parameters;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.connection.channel.forwarded.RemotePortForwarder;
import net.schmizz.sshj.connection.channel.forwarded.RemotePortForwarder.Forward;
import net.schmizz.sshj.connection.channel.forwarded.SocketForwardingConnectListener;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.xfer.FileSystemFile;


public class SSHJAppDemo {

    public static SSHClient loginSftp(String host, int port, String username, String password) throws Exception {
        SSHClient client = new SSHClient();
        client.addHostKeyVerifier(new PromiscuousVerifier());
        client.connect(host, port);
        client.authPassword(username, password);
        return client;
    }

    public static SSHClient loginPubKey(String host, String username) throws IOException {
        final SSHClient client = new SSHClient();
        client.addHostKeyVerifier(new PromiscuousVerifier());
        client.loadKnownHosts();
        client.connect("localhost");
        client.authPublickey(username);
        return client;
    }

    public static String executeCommand(SSHClient sshClient) throws IOException {
        String response = "";
        try (Session session = sshClient.startSession()) {
            final Command cmd = session.exec("ping -c 1 yourwebsiteurl.com");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(cmd.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
            cmd.join(5, TimeUnit.SECONDS);
            response = "SUCCESS";
            System.out.println("\n** exit status: " + cmd.getExitStatus());
        }
        return response;
    }

    public static String scpUpload(SSHClient ssh) throws IOException {
        String response = "";
        ssh.useCompression();

        final String src = System.getProperty("user.home") + File.separator + "test_file.txt";
        ssh.newSCPFileTransfer()
            .upload(new FileSystemFile(src), "/tmp/");
        response = "SUCCESS";

        return response;
    }

    public static String scpDownLoad(SSHClient ssh) throws IOException {
        String response = "";
        ssh.newSCPFileTransfer()
            .download("/tmp/test_file.txt", new FileSystemFile(System.getProperty("user.home")));
        response = "SUCCESS";
        return response;
    }

    public static String SFTPUpload(SSHClient ssh) throws IOException {
        String response = "";
        ssh.useCompression();

        final String src = System.getProperty("user.home") + File.separator + "test_file.txt";
        ssh.newSCPFileTransfer()
            .upload(new FileSystemFile(src), "/tmp/");
        response = "SUCCESS";
        return response;
    }

    public static String SFTPDownLoad(SSHClient ssh) throws IOException {
        String response = "";
        final SFTPClient sftp = ssh.newSFTPClient();
        try {
            sftp.get("/tmp/test_file.txt", new FileSystemFile(System.getProperty("user.home")));
            response = "SUCCESS";
        } finally {
            sftp.close();
        }
        return response;
    }

    public static LocalPortForwarder localPortForwarding(SSHClient ssh) throws IOException, InterruptedException {

        LocalPortForwarder locForwarder;
        final Parameters params = new Parameters("localhost", 8081, "remote_host", 80);
        final ServerSocket ss = new ServerSocket();
        ss.setReuseAddress(true);
        ss.bind(new InetSocketAddress(params.getLocalHost(), params.getLocalPort()));

        try {
            locForwarder = ssh.newLocalPortForwarder(params, ss);
            locForwarder.listen();
        } finally {
            ss.close();
        }

        return locForwarder;
    }

    public static String remotePortForwarding(SSHClient ssh) throws IOException, InterruptedException {
        String response = "";
        ssh.getConnection()
            .getKeepAlive()
            .setKeepAliveInterval(5);
        try {
            RemotePortForwarder rpf = ssh.getRemotePortForwarder();
            ssh.getRemotePortForwarder()
                .bind(
                    new Forward(8080),
                    new SocketForwardingConnectListener(new InetSocketAddress("remote_host", 80)));

            ssh.getTransport()
                .join();
            response = "suceess";

        } finally {
            ssh.disconnect();
        }
        return response;
    }

    public static String KeepAlive(String hostName, String userName, String password) throws IOException, InterruptedException {
        String response = "";
        DefaultConfig defaultConfig = new DefaultConfig();
        defaultConfig.setKeepAliveProvider(KeepAliveProvider.KEEP_ALIVE);
        final SSHClient ssh = new SSHClient(defaultConfig);
        try {
            ssh.addHostKeyVerifier(new PromiscuousVerifier());
            ssh.getConnection()
            .getKeepAlive()
            .setKeepAliveInterval(5);
            ssh.connect(hostName, 22);
            ssh.authPassword(userName, password);

            try (Session session = ssh.startSession();) {
                session.allocateDefaultPTY();
                new CountDownLatch(1).await();
                session.allocateDefaultPTY();
            }
            response = "success";
        } finally {
            ssh.disconnect();
        }
        return response;
    }
}
