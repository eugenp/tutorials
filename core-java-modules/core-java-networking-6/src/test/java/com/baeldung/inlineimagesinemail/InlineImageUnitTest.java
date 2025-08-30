package com.baeldung.inlineimagesinemail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.mail.BodyPart;
import jakarta.mail.Multipart;
import jakarta.mail.Part;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;

import org.junit.jupiter.api.Test;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;

class InlineImageUnitTest {

    @Test
    void givenHtmlEmailWithInlineImage_whenSentViaGreenMailSmtp_thenReceivesEmailWithInlineImage() throws Exception {
        GreenMail greenMail = new GreenMail(ServerSetupTest.SMTP);
        greenMail.start();
        InlineImage inlineImage = new InlineImage();
        Session session = inlineImage.smtpsession(greenMail);

        String to = "receiver@localhost";
        String subject = "Test Subject";
        String body = """
             <p>Welcome to Baeldung, home of Java and its frameworks.</p>
             <img src='cid:image1'></img>
             <p> Explore and learn. </p>
            """;
        String imagePath = "src/main/resources/image/java.png";

        inlineImage.sendEmail(session, to, subject, body, imagePath);

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertEquals(1, receivedMessages.length);

        MimeMessage message = receivedMessages[0];

        Multipart multipart = (Multipart) message.getContent();
        assertEquals(2, multipart.getCount());

        BodyPart htmlPart = multipart.getBodyPart(0);
        assertTrue(htmlPart.getContentType()
            .contains("text/html"));
        String htmlContent = (String) htmlPart.getContent();
        assertTrue(htmlContent.contains("cid:image1"));

        BodyPart imagePart = multipart.getBodyPart(1);
        String contentId = imagePart.getHeader("Content-ID")[0];
        assertTrue(contentId.contains("image1") || contentId.contains("<image1>"));

        assertEquals(Part.INLINE, imagePart.getDisposition());
        greenMail.stop();
    }

}