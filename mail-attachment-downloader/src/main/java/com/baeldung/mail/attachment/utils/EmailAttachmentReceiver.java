package com.baeldung.mail.attachment.utils;

import static java.util.Arrays.asList;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;

public class EmailAttachmentReceiver {

    private static final String MAIL_POP3_PORT = "mail.pop3.port";
    private static final String MAIL_POP3_HOST = "mail.pop3.host";
    private static final String MAIL_POP3_SOCKET_FACTORY_PORT = "mail.pop3.socketFactory.port";
    private static final String MAIL_POP3_SOCKET_FACTORY_FALLBACK = "mail.pop3.socketFactory.fallback";
    private static final String MAIL_POP3_SOCKET_FACTORY_CLASS = "mail.pop3.socketFactory.class";
    private static final String FALSE = "false";
    private static final String JAVAX_NET_SSL_SSL_SOCKET_FACTORY = "javax.net.ssl.SSLSocketFactory";
    private static final String POP_GMAIL_COM = "pop.gmail.com";
    private static final String MULTIPART = "multipart";
    private static final String INBOX = "INBOX";
    private static final String POP3 = "pop3";
    private static final int POP3_PORT = 995;

    private String userName;
    private String password;

    public EmailAttachmentReceiver(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void downloadEmailAttachmentsTo(String directory) throws NoSuchProviderException, MessagingException, IOException {
        Properties properties = new Properties();

        // server settings
        properties.put(MAIL_POP3_HOST, POP_GMAIL_COM);
        properties.put(MAIL_POP3_PORT, POP3_PORT);

        // SSL settings
        properties.setProperty(MAIL_POP3_SOCKET_FACTORY_CLASS, JAVAX_NET_SSL_SSL_SOCKET_FACTORY);
        properties.setProperty(MAIL_POP3_SOCKET_FACTORY_FALLBACK, FALSE);
        properties.setProperty(MAIL_POP3_SOCKET_FACTORY_PORT, String.valueOf(POP3_PORT));

        // obtaining sessions
        Session session = Session.getDefaultInstance(properties);

        // connecting to the mail server
        Store store = session.getStore(POP3);
        store.connect(this.userName, this.password);

        // opens the inbox folder
        Folder inbox = store.getFolder(INBOX);
        inbox.open(Folder.READ_ONLY);
        List<Message> messages = asList(inbox.getMessages());
        for (Message message : messages) {
            // filtering message with content type 'multipart'
            if (message.getContentType()
                .equalsIgnoreCase(MULTIPART)) {
                Multipart multipart = (Multipart) message.getContent();
                for (int partCount = 0; partCount < multipart.getCount(); partCount++) {
                    MimeBodyPart mimeBodyPart = (MimeBodyPart) multipart.getBodyPart(partCount);
                    if (Part.ATTACHMENT.equalsIgnoreCase(mimeBodyPart.getDisposition())) {
                        // this is the attachment.
                        mimeBodyPart.saveFile(directory + File.separator + mimeBodyPart.getFileName());
                    }
                }
            }
        }
        // closing open connections
        inbox.close();
        store.close();
    }
}
