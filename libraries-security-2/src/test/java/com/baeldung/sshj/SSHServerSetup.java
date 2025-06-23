package com.baeldung.sshj;

import org.apache.sshd.common.config.keys.AuthorizedKeyEntry;
import org.apache.sshd.common.config.keys.PublicKeyEntry;
import org.apache.sshd.common.file.virtualfs.VirtualFileSystemFactory;
import org.apache.sshd.common.forward.PortForwardingEventListener;
import org.apache.sshd.scp.server.ScpCommandFactory;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.auth.UserAuthFactory;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.auth.pubkey.PublickeyAuthenticator;
import org.apache.sshd.server.forward.AcceptAllForwardingFilter;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.shell.ProcessShellFactory;
import org.apache.sshd.server.subsystem.SubsystemFactory;
import org.apache.sshd.sftp.server.SftpSubsystemFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SSHServerSetup {

    public static final int SSH_PORT = 2222; // Choose any available port
    public static final String HOST = "localhost";
    public static final String USERNAME = "testuser";
    public static final String PASSWORD = "testpassword";
    public static final String UPLOAD_DIRECTORY = "/upload/";
    private static final Map<String, PublicKeyEntry> authorizedKeys = new ConcurrentHashMap<>();

    public static SshServer setupServer() {
        Path resourcesPath = Paths.get("src", "main", "resources");
        Path realDirectory = resourcesPath.resolve("home")
            .toAbsolutePath();
        SshServer sshd = null;
        try {
            addAuthorizedKey(USERNAME,
                "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQCxv4COUoJYtz6S1PEUSBvNWP+uy1dmDXek+f6L+wzPs6VzAa+pvn/Qxf7gm7OUVZtvkrxsgaJ0jPfWKFOnqvyO4p/UrpaLTIyqZdD4Rr+uIUW5qn14oiVqbqEOst21oX/NiJOD/WMCeONdLzkKxWpZ7waaD1dHiaYS69FHQTWNTA7D0pcWZlv7+8x2tY3toAUFO1Jq+bAmq1mkShrSojfbWCD1mNgy3DZp+rDxrbhvpQQYs1bcrx3h9WsmFSSIFKhNio+qGPo8xjDhPXSVedK/V1XWhI6SCHLtCmoktvjQnbr6TGv/Nao5gbqKnoBIV3gsuJryOXO95MIB2i8nvRjtmqFYNezYLfNtcdY0OpkEVGaFwSD4852NYS2FVmefdK3ZX5iCBkIZC6wH09T6ysaBFtSEnv87tFmjEBD4NDP8CdWiv750meZOb7ip6X5DWfhzFJgJ52us3tM0/+BVJ8iIqqG1lqCtZig4TI7i7r+RU7XDhQtpOVjJ6Q/3oxr98Wc= testuser@localhost");

            sshd = SshServer.setUpDefaultServer();
            sshd.setPort(SSH_PORT);
            sshd.setHost(HOST);

            sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider());

            sshd.setPublickeyAuthenticator(new PublickeyAuthenticator() {

                @Override
                public boolean authenticate(String username, PublicKey publicKey, ServerSession session) {
                    PublicKeyEntry authorizedKey = authorizedKeys.get(username);
                    return authorizedKey != null && authorizedKey.equals(publicKey);
                }
            });

            sshd.setPasswordAuthenticator(new PasswordAuthenticator() {

                @Override
                public boolean authenticate(String username, String password, org.apache.sshd.server.session.ServerSession session) {
                    return username.equals(USERNAME) && password.equals(PASSWORD);
                }
            });

            List<SubsystemFactory> subFactList = new ArrayList<SubsystemFactory>();

            subFactList.add(new SftpSubsystemFactory());

            sshd.setSubsystemFactories(subFactList);

            VirtualFileSystemFactory fsFactory = new VirtualFileSystemFactory();
            fsFactory.setUserHomeDir(USERNAME, realDirectory);
            sshd.setFileSystemFactory(fsFactory);

            sshd.setShellFactory(new ProcessShellFactory("/bin/sh", "-i", "-l"));
            
            sshd.setCommandFactory(new ScpCommandFactory());

            sshd.setPublickeyAuthenticator(null);
            
            List<UserAuthFactory> userAuthFactories = new ArrayList<UserAuthFactory>();

            sshd.setUserAuthFactories(userAuthFactories);
            
            sshd.addPortForwardingEventListener(new PortForwardingEventListener() {
            });
            
            sshd.setForwardingFilter(AcceptAllForwardingFilter.INSTANCE);

            sshd.start();

            System.out.println("SSH server started on port " + SSH_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sshd;
    }

    private static void addAuthorizedKey(String username, String publicKeyString) {
        PublicKeyEntry publicKey = AuthorizedKeyEntry.parsePublicKeyEntry(publicKeyString);
        authorizedKeys.put(username, publicKey);
    }
}
