package com.baeldung.java.io.accessemailwithimap;

import jakarta.mail.Store;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Flags;
import jakarta.mail.search.FlagTerm;
import jakarta.mail.search.FromStringTerm;
import jakarta.mail.search.SearchTerm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class GmailApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(GmailApp.class);

    public static Store establishConnection() throws MessagingException {
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");

        Session session = Session.getDefaultInstance(props, null);

        Store store = session.getStore("imaps");
        store.connect("imap.googlemail.com", "EMAIL", "APP PASSWORD");
        return store;
    }

    public static void emailCount(Store store) throws MessagingException {
        Folder inbox = store.getFolder("inbox");
        Folder spam = store.getFolder("[Gmail]/Spam");
        inbox.open(Folder.READ_ONLY);
        LOGGER.info("No of Messages : " + inbox.getMessageCount());
        LOGGER.info("No of Unread Messages : " + inbox.getUnreadMessageCount());
        LOGGER.info("No of Messages in spam : " + spam.getMessageCount());
        LOGGER.info("No of Unread Messages in spam : " + spam.getUnreadMessageCount());
        inbox.close(true);
    }

    public static void readEmails(Store store) throws MessagingException, IOException {
        Folder inbox = store.getFolder("inbox");
        inbox.open(Folder.READ_ONLY);
        Message[] messages = inbox.getMessages();
        if (messages.length > 0) {
            Message message = messages[0];
            LOGGER.info("Subject: " + message.getSubject());
            LOGGER.info("From: " + Arrays.toString(message.getFrom()));
            LOGGER.info("Text: " + message.getContent());
        }
        inbox.close(true);
    }

    public static void searchEmails(Store store, String from) throws MessagingException {
        Folder inbox = store.getFolder("inbox");
        inbox.open(Folder.READ_ONLY);
        SearchTerm senderTerm = new FromStringTerm(from);
        Message[] messages = inbox.search(senderTerm);
        Message[] getFirstFiveEmails = Arrays.copyOfRange(messages, 0, 5);
        for (Message message : getFirstFiveEmails) {
            LOGGER.info("Subject: " + message.getSubject());
            LOGGER.info("From: " + Arrays.toString(message.getFrom()));
        }
        inbox.close(true);
    }

    public static void deleteEmail(Store store) throws MessagingException {
        Folder inbox = store.getFolder("inbox");
        inbox.open(Folder.READ_WRITE);
        Message[] messages = inbox.getMessages();

        if (messages.length >= 7) {
            Message seventhLatestMessage = messages[messages.length - 7];

            seventhLatestMessage.setFlag(Flags.Flag.DELETED, true);
            LOGGER.info("Delete the seventh message: " + seventhLatestMessage.getSubject());
        } else {
            LOGGER.info("There are less than seven messages in the inbox.");
        }
        inbox.close(true);
    }

    public static void markLatestUnreadAsRead(Store store) throws MessagingException {
        Folder inbox = store.getFolder("inbox");
        inbox.open(Folder.READ_WRITE);

        Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
        if (messages.length > 0) {
            Message latestUnreadMessage = messages[messages.length - 1];
            latestUnreadMessage.setFlag(Flags.Flag.SEEN, true);
        }
        inbox.close(true);
    }

    public static void moveToFolder(Store store, Message message, String folderName) throws MessagingException {
        Folder destinationFolder = store.getFolder(folderName);
        if (!destinationFolder.exists()) {
            destinationFolder.create(Folder.HOLDS_MESSAGES);
        }
        Message[] messagesToMove = new Message[] { message };
        message.getFolder()
            .copyMessages(messagesToMove, destinationFolder);
        message.setFlag(Flags.Flag.DELETED, true);
    }

}
