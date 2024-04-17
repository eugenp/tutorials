/**
 * 
 */
package com.baeldung.sshj;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.sshd.common.file.virtualfs.VirtualFileSystemFactory;
import org.apache.sshd.scp.server.ScpCommandFactory;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.subsystem.SubsystemFactory;
import org.apache.sshd.sftp.server.SftpSubsystemFactory;

public class SSHServerExample {

    public static final int SSH_PORT = 2222; // Choose any available port
    public static final String HOST = "localhost";
    public static final String USERNAME = "testuser";
    public static final String PASSWORD = "testpassword";
    public static final String UPLOAD_DIRECTORY = "/upload/";

    public static void main(String[] args) throws IOException {
        // Set up the SSH server
        SshServer sshd = SshServer.setUpDefaultServer();
        sshd.setPort(SSH_PORT); // Set the port to listen on
        sshd.setHost(HOST);
        sshd.setPasswordAuthenticator(new MyPasswordAuthenticator()); // Set password authenticator
        sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(Paths.get("hostkey.ser"))); // Generate host key

        // Set up virtual file system for SFTP  
        Path rootDir = Paths.get("/root/upload");
        Files.createDirectories(rootDir); // Create root directory if it doesn't exist
        sshd.setFileSystemFactory(new VirtualFileSystemFactory(rootDir));

        // Set up SCP and SFTP support
        List<SubsystemFactory> subFactList = new ArrayList<SubsystemFactory>();
        subFactList.add(new SftpSubsystemFactory());
        //subFactList.add(new ScpCommandFactory());

        sshd.setSubsystemFactories(subFactList);
        sshd.setCommandFactory(new ScpCommandFactory());

        // Start the SSH server
        sshd.start();
        System.out.println("SSH server started on port " + sshd.getPort());
        System.out.println("SSH server started on host " + sshd.getHost());

        // Keep the program running until it's terminated
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Custom password authenticator
    static class MyPasswordAuthenticator implements PasswordAuthenticator {

        @Override
        public boolean authenticate(String username, String password, org.apache.sshd.server.session.ServerSession session) {
            // You can implement your own logic to authenticate users
            // For demonstration purposes, let's authenticate any user with password "password"
            return username.equals(USERNAME) && password.equals(PASSWORD);
        }
    }
}
