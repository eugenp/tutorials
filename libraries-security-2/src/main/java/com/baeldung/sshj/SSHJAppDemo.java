package com.baeldung.sshj;

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
import net.schmizz.sshj.userauth.keyprovider.KeyProvider;
import net.schmizz.sshj.xfer.FileSystemFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class SSHJAppDemo {

    public static SSHClient loginSftp(String host, int port, String username, String password) throws Exception {
        SSHClient client = new SSHClient();
        client.addHostKeyVerifier(new PromiscuousVerifier());
        client.connect(host, port);
        client.authPassword(username, password);
        return client;
    }

    public static SSHClient loginPubKey(String host, String username, String privateFilePath, int port) throws IOException {
        final SSHClient client = new SSHClient();
        File privateKeyFile = new File(privateFilePath + "/private_key");
        try {
            KeyProvider privateKey = client.loadKeys(privateKeyFile.getPath());
            client.addHostKeyVerifier(new PromiscuousVerifier());
            client.connect(host, port);
            client.authPublickey(username, privateKey);
            System.out.println(client.getRemoteHostname());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }

    public static String executeCommand(SSHClient sshClient) throws IOException {
        String response = "";
        try (Session session = sshClient.startSession()) {
            final Command cmd = session.exec("ls -lsa");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(cmd.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
            cmd.join(5, TimeUnit.SECONDS);
            response = "success";
            System.out.println("\n** exit status: " + cmd.getExitStatus());
        }
        return response;
    }

    public static void scpUpload(SSHClient ssh, String filePath) throws IOException {
        ssh.useCompression();
        ssh.newSCPFileTransfer()
            .upload(new FileSystemFile(filePath), "/upload/");
    }

    public static void scpDownLoad(SSHClient ssh, String downloadPath, String fileName) throws IOException {
        ssh.useCompression();
        ssh.newSCPFileTransfer()
            .download("/upload/" + fileName, downloadPath);
    }

    public static void SFTPUpload(SSHClient ssh, String filePath) throws IOException {
        final SFTPClient sftp = ssh.newSFTPClient();
        sftp.put(new FileSystemFile(filePath), "/upload/");
    }

    public static void SFTPDownLoad(SSHClient ssh, String downloadPath, String fileName) throws IOException {
        final SFTPClient sftp = ssh.newSFTPClient();
        try {
            sftp.get("/upload/" + fileName, downloadPath);
        } finally {
            sftp.close();
        }
    }

    public static LocalPortForwarder localPortForwarding(SSHClient ssh) throws IOException, InterruptedException {
        LocalPortForwarder locForwarder;
        final Parameters params = new Parameters(ssh.getRemoteHostname(), 8081, "google.com", 80);
        final ServerSocket ss = new ServerSocket();
        ss.setReuseAddress(true);
        ss.bind(new InetSocketAddress(params.getLocalHost(), params.getLocalPort()));

        locForwarder = ssh.newLocalPortForwarder(params, ss);
        new Thread(() -> {
            try {
                locForwarder.listen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        return locForwarder;
    }

    public static RemotePortForwarder remotePortForwarding(SSHClient ssh) throws IOException, InterruptedException {
        RemotePortForwarder rpf;
        ssh.getConnection()
            .getKeepAlive()
            .setKeepAliveInterval(5);

        rpf = ssh.getRemotePortForwarder();

        new Thread(() -> {
            try {
                rpf.bind(new Forward(8083), new SocketForwardingConnectListener(new InetSocketAddress("google.com", 80)));
                ssh.getTransport()
                    .join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        return rpf;
    }

    public static String KeepAlive(String hostName, String userName, String password) throws IOException, InterruptedException {
        String response = "";
        DefaultConfig defaultConfig = new DefaultConfig();
        defaultConfig.setKeepAliveProvider(KeepAliveProvider.KEEP_ALIVE);
        final SSHClient ssh = new SSHClient(defaultConfig);

        try {
            ssh.addHostKeyVerifier(new PromiscuousVerifier());

            ssh.connect(hostName, 22);
            ssh.getConnection()
                .getKeepAlive()
                .setKeepAliveInterval(5);
            ssh.authPassword(userName, password);

            Session session = ssh.startSession();
            session.allocateDefaultPTY();
            new CountDownLatch(1).await();
            try {
                session.allocateDefaultPTY();
            } finally {
                session.close();
            }
        } finally {
            ssh.disconnect();
        }

        response = "success";

        return response;
    }
}
