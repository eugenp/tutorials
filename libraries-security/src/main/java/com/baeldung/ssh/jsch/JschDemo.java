package com.baeldung.ssh.jsch;

import java.io.ByteArrayOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class JschDemo {
    protected static final Logger log = LoggerFactory.getLogger(JschDemo.class);

    public static void main(String[] args) throws Exception {
        String username = "demo";
        String password = "password";
        String host = "test.rebex.net";
        int port = 22;
        String command = "ls";
        listFolderStructure(username, password, host, port, command);
    }

    public static String listFolderStructure(String username, String password, String host, int port, String command)
            throws Exception {
        // The resource type Session does not implement java.lang.AutoCloseable
        Session session = null;
        ChannelExec channel = null;
        String response = null;

        try {
            session = new JSch().getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            ByteArrayOutputStream errorResponseStream = new ByteArrayOutputStream();
            channel.setOutputStream(responseStream);
            channel.setErrStream(errorResponseStream);
            channel.connect();
            while (channel.isConnected()) {
                Thread.sleep(100);
            }
            String errorResponse = new String(errorResponseStream.toByteArray());
            response = new String(responseStream.toByteArray());
            if (!errorResponse.isEmpty()) {
                throw new Exception(errorResponse);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
        return response;
    }

}