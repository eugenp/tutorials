package com.baeldung.java.io.accessemailwithimap;

import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Store;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


class GmailAppLiveTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(GmailApp.class);
    Store store = null;
    Folder folder = null;

    @BeforeEach
    void setUp() throws MessagingException {
        store = GmailApp.establishConnection();
        folder = store.getFolder("inbox");
    }

    @AfterEach
    void tearDown() throws MessagingException {
        if (store != null) {
            store.close();
        }
        if (folder != null && folder.isOpen()) {
            folder.close(true);
        }
    }

    @Test
    void givenEmails_whenCountEmails_thenEmailsCounted() throws MessagingException {
        GmailApp.emailCount(store);
    }

    @Test
    void givenEmails_whenReadEmails_thenEmailsRead() throws MessagingException, IOException {
        GmailApp.readEmails(store);
    }

    @Test
    void givenEmails_whenSearchEmails_thenEmailsSearched() throws MessagingException {
        GmailApp.searchEmails(store, "Google");
    }

    @Test
    void givenEmails_whenLastEmailsIsUnread_thenEmailsRead() throws MessagingException, IOException {
        GmailApp.markLatestUnreadAsRead(store);
    }

    @Test
    void givenEmails_whenDeleting_thenEmailsDeleted() throws MessagingException {
        GmailApp.deleteEmail(store);
    }

    @Test
    void givenEmails_whenMoveEmails_thenEmailsMoved() throws MessagingException {
        folder.open(Folder.READ_WRITE);
        Message[] messages = folder.getMessages();
        if (messages.length > 0) {
            Message message = messages[0];
            GmailApp.moveToFolder(store, message, "First");
        }

    }

}