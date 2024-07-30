package com.baeldung.sendgrid;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

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
public class EmailDispatcher {

    private final SendGrid sendGrid;
    private final Email fromEmail;

    public EmailDispatcher(SendGrid sendGrid, Email fromEmail) {
        this.sendGrid = sendGrid;
        this.fromEmail = fromEmail;
    }

    public void dispatchEmail(String emailId, String subject, String body) throws IOException {
        Email toEmail = new Email(emailId);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(fromEmail, subject, toEmail, content);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        sendGrid.api(request);
    }

    public void dispatchEmail(String emailId, String subject, String body, List<MultipartFile> files)
            throws IOException {
        Email toEmail = new Email(emailId);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(fromEmail, subject, toEmail, content);

        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                Attachments attachments = createAttachment(file);
                mail.addAttachments(attachments);
            }
        }

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        sendGrid.api(request);
    }

    private Attachments createAttachment(MultipartFile file) throws IOException {
        Attachments attachments = new Attachments();
        attachments.setDisposition("attachment");
        attachments.setType(file.getContentType());
        attachments.setFilename(file.getOriginalFilename());
        attachments.setContent(Base64.getEncoder().encodeToString(file.getBytes()));
        return attachments;
    }

}