package com.baeldung.readfileremotely;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.baeldung.readfileremotely.RemoteServerJsch.*;

class RemoteServerJschLiveTest {

    private static final String REMOTE_DIR = "REMOTE_DIR";

    @Test
    public void givenJsch_whenListFiles_thenSuccess() throws JSchException, SftpException, IOException {
        JSch jsch = setUpJsch();
        Session session = createSession(jsch);
        ChannelSftp channelSftp = createSftpChannel(session);
        readFileLineByLine(channelSftp, REMOTE_DIR + "/examplefile.txt");
        disconnect(channelSftp, session);
    }
}