package com.baeldung.spring.mail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import jakarta.mail.MessagingException;

/**
 * Created by Olga on 8/22/2016.
 */
public interface EmailService {
    void sendSimpleMessage(String to,
                           String subject,
                           String text);

    void sendMessageWithAttachment(String to,
        String subject,
        String text,
        String pathToAttachment);

    void sendMessageWithInputStreamAttachment(
        String to, String subject, String text, String attachmentName, InputStream inputStream);
}
