package com.baeldung.mail.attachment;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailAttachmentReceiver {

    final Logger LOGGER = LoggerFactory.getLogger(EmailAttachmentReceiver.class);

    private static final String MAIL_PROTOCOL_PORT = "mail.%s.port";
    private static final String MAIL_PROTOCOL_HOST = "mail.%s.host";
    private static final String MAIL_PROTOCOL_SOCKET_FACTORY_PORT = "mail.%s.socketFactory.port";
    private static final String MAIL_PROTOCOL_SOCKET_FACTORY_FALLBACK = "mail.%s.socketFactory.fallback";
    private static final String MAIL_PROTOCOL_SOCKET_FACTORY_CLASS = "mail.%s.socketFactory.class";
    private static final String FALSE = "false";
    private static final String JAVAX_NET_SSL_SSL_SOCKET_FACTORY = "javax.net.ssl.SSLSocketFactory";
    private static final String MULTIPART = "multipart";
    private static final String INBOX = "INBOX";

    private String protocol;
    private String host;
    private String port;
    private String userName;
    private String password;

    public EmailAttachmentReceiver(String host, String protocol, String port, String userName, String password) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }

    public void downloadEmailAttachmentsTo(String directory) {
        Properties properties = new Properties();

        // server settings
        properties.put(String.format(MAIL_PROTOCOL_HOST, this.protocol), this.host);
        properties.put(String.format(MAIL_PROTOCOL_PORT, this.protocol), this.port);

        // SSL settings
        properties.setProperty(String.format(MAIL_PROTOCOL_SOCKET_FACTORY_CLASS, this.protocol), JAVAX_NET_SSL_SSL_SOCKET_FACTORY);
        properties.setProperty(String.format(MAIL_PROTOCOL_SOCKET_FACTORY_FALLBACK, this.protocol), FALSE);
        properties.setProperty(String.format(MAIL_PROTOCOL_SOCKET_FACTORY_PORT, this.protocol), this.port);

        // obtaining sessions
        Session session = Session.getDefaultInstance(properties);
        try {
            // connecting to the mail server
            Store store = session.getStore(this.protocol);
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
        } catch (NoSuchProviderException noSuchProviderException) {
            LOGGER.error("An error occurred while downloading attachments", noSuchProviderException);
        } catch (MessagingException messagingException) {
            LOGGER.error("An error occurred while downloading attachments", messagingException);
        } catch (IOException ioException) {
            LOGGER.error("An error occurred while downloading attachments", ioException);
        }
    }
}
