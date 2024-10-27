package com.baeldung.readfileremotely;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RemoteServerJsch {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteServerJsch.class);
    private static final String HOST = "HOST";
    private static final String USER = "USERNAME";
    private static final String PRIVATE_KEY = "PATH TO PRIVATE KEY";
    private static final int PORT = 22;

    public static JSch setUpJsch() throws JSchException {
        JSch jsch = new JSch();
        jsch.addIdentity(PRIVATE_KEY);
        return jsch;
    }

    public static Session createSession(JSch jsch) throws JSchException {
        Session session = jsch.getSession(USER, HOST, PORT);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        return session;
    }

    public static ChannelSftp createSftpChannel(Session session) throws JSchException {
        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();
        return channelSftp;
    }

    public static void readFileLineByLine(ChannelSftp channelSftp, String filePath) throws SftpException, IOException {
        InputStream stream = channelSftp.get(filePath);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                LOGGER.info(line);
            }
        }
    }

    public static void disconnect(ChannelSftp channelSftp, Session session) {
        channelSftp.disconnect();
        session.disconnect();
    }
}
