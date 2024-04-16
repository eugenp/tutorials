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
            .withSubject("Email with Plain Text!")
            .withPlainText("This is a test email sent using SJM.")
            .buildEmail();
        sendEmail(email);
    }

    public static void sendPlainTextEmailToMultipleRecipient() {
        Email email = EmailBuilder.startingBlank()
            .from("sender@example.com")
            .to("recipient1@example.com, recipient2@example.com, recipient3@example.com")
            .withSubject("Email with Plain Text!")
            .withPlainText("This is a test email sent using SJM to multiple recipients.")
            .buildEmail();
        sendEmail(email);
    }

    public static void sendEmailWithAttachment() {
        Email email = EmailBuilder.startingBlank()
            .from("sender@example.com")
            .to("recipient@example.com")
            .withSubject("Email with Plain Text and Attachment!")
            .withPlainText("This is a test email with attachment sent using SJM.")
            .withAttachment("important_document.pdf", new FileDataSource("path/to/important_document.pdf"))
            .buildEmail();
        sendEmail(email);
    }

    public static void sendEmailWithMultipleAttachment() {
        List<AttachmentResource> arList = new ArrayList<>();
        arList.add(new AttachmentResource("important_document.pdf", new FileDataSource("path/to/important_document.pdf")));
        arList.add(new AttachmentResource("company_logo.png", new FileDataSource("path/to/company_logo.png")));
        Email email = EmailBuilder.startingBlank()
            .from("sender@example.com")
            .to("recipient@example.com")
            .withSubject("Email with Plain Text and multiple Attachments!")
            .withPlainText("This is a test email with attachment sent using SJM.")
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
            .withSubject("Email with HTML and Embedded Image!")
            .withHTMLText(htmlContent)
            .withEmbeddedImage("company_logo", new FileDataSource("path/to/company_logo.png"))
            .buildEmail();
        sendEmail(email);
    }

    public static void replyingToEmail(Email receivedEmail) {
        EmailBuilder.replyingTo(receivedEmail)
            .from("sender@example.com")
            .prependText("This is a Reply Email. Original email included below:")
            .buildEmail();
    }

    public static void forwardingEmail(Email receivedEmail) {
        Email email = EmailBuilder.forwarding(receivedEmail)
            .from("sender@example.com")
            .prependText("This is an Forward Email. See below email:")
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
            .withSubject("Email with Custom Header")
            .withPlainText("This is an important message.")
            .withHeader("X-Priority", "1")
            .buildEmail();
        sendEmail(email);
    }

    private static void sendEmailWithDeliveryReadRecipient() {
        Email email = EmailBuilder.startingBlank()
            .from("sender@example.com")
            .to("recipient@example.com")
            .withSubject("Email with Delivery/Read Receipt Configured!")
            .withPlainText("This is an email sending with delivery/read receipt.")
            .withDispositionNotificationTo(new Recipient("name", "address@domain.com", Message.RecipientType.TO))
            .withReturnReceiptTo(new Recipient("name", "address@domain.com", Message.RecipientType.TO))
            .buildEmail();

        sendEmail(email);
    }

    private static void sendEmail(Email email) {
        Mailer mailer = MailerBuilder.withSMTPServer("smtp.example.com", 25, "username", "password")
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
