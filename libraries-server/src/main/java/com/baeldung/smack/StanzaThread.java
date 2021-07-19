package com.baeldung.smack;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.impl.JidCreate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StanzaThread implements Runnable {

    private Logger logger = LoggerFactory.getLogger(StanzaThread.class);

    @Override
    public void run() {
        XMPPTCPConnectionConfiguration config = null;
        try {
            config = XMPPTCPConnectionConfiguration.builder()
                    .setUsernameAndPassword("baeldung2","baeldung2")
                    .setXmppDomain("jabb3r.org")
                    .setHost("jabb3r.org")
                    .build();

            AbstractXMPPConnection connection = new XMPPTCPConnection(config);
            connection.connect();
            connection.login();

            ChatManager chatManager = ChatManager.getInstanceFor(connection);

            Chat chat = chatManager.chatWith(JidCreate.from("baeldung@jabb3r.org").asEntityBareJidOrThrow());

            chat.send("Hello!");

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
