package com.baeldung.mail;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.ServerSetupTest;

import jakarta.mail.Address;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class EmailServiceLiveTest {

    @Rule
    public final GreenMailRule greenMail = new GreenMailRule(ServerSetupTest.SMTP);

    private EmailService emailService;

    @Before
    public void setup() {
        emailService = new EmailService("localhost", greenMail.getSmtp()
            .getPort());
    }

    @Test
    public void givenEmailMessageWithAttachment_whenEmailIsSent_MessageIsReceived() throws Exception {

        emailService.sendMail();

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertEquals(1, receivedMessages.length);

        MimeMessage receivedMessage = receivedMessages[0];
        assertEquals("Mail Subject", subjectFromMessage(receivedMessage));
        assertEquals("This is my first email using JavaMailer", emailTextFrom(receivedMessage));
        assertEquals("This is my <b style='color:red;'>bold-red email</b> using JavaMailer", emailStyledTextFrom(receivedMessage));
        assertEquals("sample attachment content", attachmentContentsFrom(receivedMessage));
    }

    @Test
    public void givenEmailMessageWithMultipleRecipients_whenEmailIsSent_AllRecipientReceivedMessage() throws Exception {
        emailService.sendMailToMultipleRecipients();

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertEquals(4, receivedMessages.length);

        for (MimeMessage mimeMessage : receivedMessages) {
            Address[] recipients = new Address[4];
            recipients[0] = new InternetAddress("to@gmail.com");
            recipients[1] = new InternetAddress("to1@gmail.com");
            recipients[2] = new InternetAddress("to2@gmail.com");
            recipients[3] = new InternetAddress("to3@gmail.com");
            assertArrayEquals(recipients, mimeMessage.getAllRecipients());
        }
    }

    private static String subjectFromMessage(MimeMessage receivedMessage) throws MessagingException {
        return receivedMessage.getSubject();
    }

    private static String emailTextFrom(MimeMessage receivedMessage) throws IOException, MessagingException {
        return ((MimeMultipart) receivedMessage.getContent()).getBodyPart(0)
            .getContent()
            .toString();
    }

    private static String emailStyledTextFrom(MimeMessage receivedMessage) throws IOException, MessagingException {
        return ((MimeMultipart) receivedMessage.getContent()).getBodyPart(1)
            .getContent()
            .toString();
    }

    private static String attachmentContentsFrom(MimeMessage receivedMessage) throws Exception {
        return ((MimeMultipart) receivedMessage.getContent()).getBodyPart(2)
            .getContent()
            .toString();
    }

}
