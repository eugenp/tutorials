package com.baeldung.java.io.simplemail;

import java.util.ArrayList;
import java.util.List;

import org.simplejavamail.api.email.AttachmentResource;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.email.Recipient;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.MailException;

import jakarta.activation.FileDataSource;
import jakarta.mail.Message;

public class SimpleMailExample {

    public static void sendPlainTextEmail() {
        Email email = EmailBuilder.startingBlank()
            .from("sender@example.com")
            .to("recipient@example.com")
            .withSubject("Hello, World!")
            .withPlainText("This is a test email sent using Simple Java Mail.")
            .buildEmail();
        sendEmail(email);
    }

    public static void sendEmailWithAttachment() {
        Email email = EmailBuilder.startingBlank()
            .from("sender@example.com")
            .to("recipient@example.com")
            .withSubject("Email with Plain Text")
            .withPlainText("This is a test email with attachment sent using Simple Java Mail.")
            .withAttachment("important_document.pdf", new FileDataSource("path/to/important_document.pdf"))
            .buildEmail();
        sendEmail(email);
    }

    public static void sendEmailWithMultipleAttachment() {
        List<AttachmentResource> arList = new ArrayList<>();
        arList.add(new AttachmentResource("important_document.pdf", new FileDataSource("path/to/important_document.pdf")));
        arList.add(new AttachmentResource("company_logo.jpg", new FileDataSource("path/to/company_logo.jpg")));
        Email email = EmailBuilder.startingBlank()
            .from("sender@example.com")
            .to("recipient@example.com")
            .withSubject("Email with Plain Text")
            .withPlainText("This is a test email with attachment sent using Simple Java Mail.")
            .withAttachments(arList)
            .buildEmail();
        sendEmail(email);
    }

    public static void sendHTMLTextWithEmbeddedImageEmail() {
        String htmlContent = "<h1>This is an email with HTML content</h1>" + "<p>This email body contains additional information and formatting.</p>" +
            "<img src=\"cid:company_logo\" alt=\"Company Logo\">";

        Email email = EmailBuilder.startingBlank()
            .from("sender@example.com")
            .to("recipient@example.com")
            .withSubject("Email with HTML and CID Image")
            .withHTMLText(htmlContent)
            .withEmbeddedImage("company_logo", new FileDataSource("path/to/image.png"))
            .buildEmail();
        sendEmail(email);
    }

    public static void replyingToEmail(Email receivedEmail) {
        EmailBuilder.replyingTo(receivedEmail)
            .from("sender@example.com")
            .prependText("Reply body. Original email included below")
            .buildEmail();
    }

    public static void forwardingEmail(Email receivedEmail) {
        EmailBuilder.forwarding(receivedEmail)
            .from("sender@example.com")
            .prependText("Hello? This is Forward. See below email:")
            .buildEmail();
    }

    public static void handleExceptionWhenSendingEmail() {
        try {
            sendPlainTextEmail();
            System.out.println("Email sent successfully!");
        } catch (MailException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void setCustomHeaderWhenSendingEmail() {
        Email email = EmailBuilder.startingBlank()
            .from("sender@example.com")
            .to("recipient@example.com")
            .withSubject("Important Message")
            .withPlainText("This is an important message.")
            .withHeader("X-Priority", "1")
            .buildEmail();
        sendEmail(email);
    }

    private static void sendEmailWithDeliveryReadRecipient() {
        Email email = EmailBuilder.startingBlank()
            .from("sender@example.com")
            .to("recipient@example.com")
            .withSubject("Important Message")
            .withPlainText("This is an important message.")
            .withDispositionNotificationTo(new Recipient("name", "address@domain.com", Message.RecipientType.TO))
            .withReturnReceiptTo(new Recipient("name", "address@domain.com", Message.RecipientType.TO))
            .buildEmail();

        sendEmail(email);
    }

    private static void sendEmail(Email email) {
        Mailer mailer = MailerBuilder
            .withSMTPServer("smtp.example.com", 25, "username", "password")
            .withMaximumEmailSize(1024 * 1024 * 5) // 5 Megabytes
            .buildMailer();
        boolean validate = mailer.validate(email);
        if (validate) {
            mailer.sendMail(email);
        } else {
            System.out.println("Invalid email address.");
        }
    }
}
