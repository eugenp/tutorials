package com.baeldung.listfilesremoteserver;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Vector;

public class RemoteServerJsch {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteServerJsch.class);
    private static final String HOST = "HOST_NAME";
    private static final String USER = "USERNAME";
    private static final String PRIVATE_KEY = "PRIVATE_KEY";
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

    public static Vector<ChannelSftp.LsEntry> listFiles(ChannelSftp channelSftp, String remoteDir) throws SftpException {
        return channelSftp.ls(remoteDir);
    }

    public static void printFiles(Vector<ChannelSftp.LsEntry> files) {
        for (ChannelSftp.LsEntry entry : files) {
            LOGGER.info(entry.getLongname());
        }
    }

    public static void disconnect(ChannelSftp channelSftp, Session session) {
        channelSftp.disconnect();
        session.disconnect();
    }
}