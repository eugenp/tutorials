package com.baeldung.spring.mail;

import java.io.IOException;
import java.util.Map;

import javax.mail.MessagingException;

import freemarker.template.TemplateException;

/**
 * @param to       Recipient Address
 * @param subject  Message subject
 * @param content  Message content
 * @param filePath Attachment address
 * @param rscPath  Static resource address
 * @param rscId    Static resource id
 * @param cc       Cc Address
 * @throws MessagingException Email sending exception
 */
public interface EmailService {

        void sendSimpleMessage(String to,
                        String subject,
                        String content);

        void sendSimpleMessage(String to,
                        String subject,
                        String content,
                        String... cc);

        void sendSimpleMessageUsingTemplate(String to,
                        String subject,
                        String... templateModel);

        void sendMessageWithAttachment(String to,
                        String subject,
                        String text,
                        String pathToAttachment);

        void sendMessageUsingThymeleafTemplate(String to,
                        String subject,
                        Map<String, Object> templateModel)
                        throws IOException, MessagingException;

        void sendMessageUsingFreemarkerTemplate(String to,
                        String subject,
                        Map<String, Object> templateModel)
                        throws IOException, TemplateException, MessagingException;

        void sendHtmlMail(String to, String subject, String content, String... cc)
                        throws MessagingException;

        void sendAttachmentsMail(String to, String subject, String content, String filePath, String... cc)
                        throws MessagingException;

        void sendResourceMail(String to, String subject, String content, String rscPath, String rscId, String... cc)
                        throws MessagingException;
}
