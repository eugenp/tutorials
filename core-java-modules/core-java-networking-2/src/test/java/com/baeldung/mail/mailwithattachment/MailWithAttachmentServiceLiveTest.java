package com.baeldung.mail.mailwithattachment;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import static org.junit.Assert.assertEquals;

public class MailWithAttachmentServiceLiveTest {

    private static final String USERNAME = "testUser";
    private static final String PASSWORD = "password";
    private static final String HOSTNAME = "localhost";

    @Rule
    public final GreenMailRule greenMail = new GreenMailRule(ServerSetupTest.SMTP)
      .withConfiguration(
        GreenMailConfiguration.aConfig()
          .withUser(USERNAME, PASSWORD)
      );

    @Resource
    private MailWithAttachmentService emailService;

    @Before
    public void setup() {
        emailService = new MailWithAttachmentService(
          USERNAME, PASSWORD, HOSTNAME, greenMail.getSmtp().getPort()
        );
    }

    @Test
    public void givenEmailService_whenMessageSentWithAttachments_thenMessageIsReceived() throws Exception {

        Session tlsSession = emailService.getSession();
        emailService.sendMail(tlsSession);

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertEquals(1, receivedMessages.length);

        MimeMessage receivedMessage = receivedMessages[0];
        assertEquals("Testing Subject", subjectFrom(receivedMessage));
        assertEquals("This is message body", emailTextFrom(receivedMessage));
        assertEquals("sample attachment content", attachment1ContentsFrom(receivedMessage));
        assertEquals("sample attachment content 2", attachment2ContentsFrom(receivedMessage));
    }

    private static String subjectFrom(MimeMessage receivedMessage) throws MessagingException {
        return receivedMessage.getSubject();
    }

    private static String emailTextFrom(MimeMessage receivedMessage) throws Exception {
        return GreenMailUtil.getBody(((MimeMultipart) receivedMessage.getContent())
          .getBodyPart(0));
    }

    private static String attachment1ContentsFrom(MimeMessage receivedMessage) throws Exception {
        return GreenMailUtil.getBody(((MimeMultipart) receivedMessage.getContent())
          .getBodyPart(1));
    }

    private static String attachment2ContentsFrom(MimeMessage receivedMessage) throws Exception {
        return GreenMailUtil.getBody(((MimeMultipart) receivedMessage.getContent())
          .getBodyPart(2));
    }

}
