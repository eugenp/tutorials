package com.baeldung.smack;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.filter.StanzaTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.jxmpp.stringprep.XmppStringprepException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SmackIntegrationTest {

    static AbstractXMPPConnection connection;
    Logger logger = LoggerFactory.getLogger(SmackIntegrationTest.class.getName());

    @BeforeClass
    public static void setup() throws IOException, InterruptedException, XMPPException, SmackException {

        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                .setUsernameAndPassword("baeldung","baeldung")
                .setXmppDomain("jabb3r.org")
                .setHost("jabb3r.org")
                .build();

        XMPPTCPConnectionConfiguration config2 = XMPPTCPConnectionConfiguration.builder()
                .setUsernameAndPassword("baeldung2","baeldung2")
                .setXmppDomain("jabb3r.org")
                .setHost("jabb3r.org")
                .build();

        connection = new XMPPTCPConnection(config);
        connection.connect();
        connection.login();

    }

    @Test
    public void whenSendMessageWithChat_thenReceiveMessage() throws XmppStringprepException, InterruptedException {

        Object cv = new Object();
        ChatManager chatManager = ChatManager.getInstanceFor(connection);
        final String[] expected = {null};

        new StanzaThread().run();


        chatManager.addIncomingListener((entityBareJid, message, chat) -> {
            logger.info("Message arrived: " + message.getBody());
            expected[0] = message.getBody();
            synchronized (cv) {
                cv.notify();
            }
        });

        synchronized (cv) {
            cv.wait();
        }
        Assert.assertEquals("Hello!", expected[0]);
    }

    @Test
    public void whenSendMessage_thenReceiveMessageWithFilter() throws XmppStringprepException, InterruptedException {

        Object cv = new Object();
        final String[] expected = {null};

        new StanzaThread().run();

        connection.addAsyncStanzaListener(stanza -> {
            if (stanza instanceof Message) {
                Message message = (Message) stanza;
                expected[0] = message.getBody();
                synchronized (cv) {
                    cv.notify();
                }
            }
        }, StanzaTypeFilter.MESSAGE);

        synchronized (cv) {
            cv.wait();
        }
        Assert.assertEquals("Hello!", expected[0]);
    }
}
