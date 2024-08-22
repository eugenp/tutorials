package com.baeldung.sendgrid;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Service
@EnableConfigurationProperties(SendGridConfigurationProperties.class)
public class EmailDispatcher {

    private static final String EMAIL_ENDPOINT = "mail/send";

    private final SendGrid sendGrid;
    private final Email fromEmail;
    private final SendGridConfigurationProperties sendGridConfigurationProperties;

    public EmailDispatcher(SendGrid sendGrid, Email fromEmail,
            SendGridConfigurationProperties sendGridConfigurationProperties) {
        this.sendGrid = sendGrid;
        this.fromEmail = fromEmail;
        this.sendGridConfigurationProperties = sendGridConfigurationProperties;
    }

    public void dispatchEmail(String emailId, String subject, String body) throws IOException {
        Email toEmail = new Email(emailId);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(fromEmail, subject, toEmail, content);

        sendRequest(mail);
    }

    public void dispatchEmail(String emailId, String subject, String body, List<MultipartFile> files)
            throws IOException {
        Email toEmail = new Email(emailId);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(fromEmail, subject, toEmail, content);

        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                Attachments attachment = createAttachment(file);
                mail.addAttachments(attachment);
            }
        }

        sendRequest(mail);
    }

    public void dispatchHydrationAlert(String emailId, String username) throws IOException {
        Email toEmail = new Email(emailId);
        String templateId = sendGridConfigurationProperties.getHydrationAlertNotification().getTemplateId();

        DynamicTemplatePersonalization personalization = new DynamicTemplatePersonalization();
        personalization.add("name", username);
        personalization.add("lastDrinkTime", "Way too long ago");
        personalization.add("hydrationStatus", "Thirsty as a camel");
        personalization.addTo(toEmail);

        Mail mail = new Mail();
        mail.setFrom(fromEmail);
        mail.setTemplateId(templateId);
        mail.addPersonalization(personalization);

        sendRequest(mail);
    }

    private void sendRequest(Mail mail) throws IOException {
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint(EMAIL_ENDPOINT);
        request.setBody(mail.build());

        sendGrid.api(request);
    }

    private Attachments createAttachment(MultipartFile file) throws IOException {
        byte[] encodedFileContent = Base64.getEncoder().encode(file.getBytes());
        Attachments attachment = new Attachments();
        attachment.setDisposition("attachment");
        attachment.setType(file.getContentType());
        attachment.setFilename(file.getOriginalFilename());
        attachment.setContent(new String(encodedFileContent, StandardCharsets.UTF_8));
        return attachment;
    }

}