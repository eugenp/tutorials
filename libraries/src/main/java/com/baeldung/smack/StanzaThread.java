package com.baeldung.smack;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;

public class StanzaThread extends Thread{


    public StanzaThread(){
    }

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

        } catch (XmppStringprepException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        }


    }
}
