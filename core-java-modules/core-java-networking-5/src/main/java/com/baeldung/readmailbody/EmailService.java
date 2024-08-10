package com.baeldung.readmailbody;

import java.io.IOException;
import java.io.InputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import jakarta.mail.BodyPart;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.Store;

public class EmailService {

    private final Session session;
    private String plainContent;
    private String htmlContent;

    public EmailService(Session session) {
        this.session = session;
    }

    public void retrieveEmails() throws MessagingException {
        try (Store store = session.getStore("imaps")) {
            store.connect("imap.gmail.com", "your_email", "your_password");
            try (Folder inbox = store.getFolder("inbox")) {
                inbox.open(Folder.READ_ONLY);
                Message[] messages = inbox.getMessages();
                for (Message message : messages) {
                    try {
                        Object content = message.getContent();
                        if (content instanceof Multipart) {
                            Multipart multipart = (Multipart) content;
                            for (int i = 0; i < multipart.getCount(); i++) {
                                BodyPart bodyPart = multipart.getBodyPart(i);
                                if (bodyPart.getContentType().toLowerCase()
                                    .startsWith("text/plain")) {
                                    plainContent = (String) bodyPart.getContent();
                                } else if (bodyPart.getContentType().toLowerCase()
                                    .startsWith("text/html")) {
                                    try (InputStream inputStream = bodyPart.getInputStream()) {
                                        String html = new String(inputStream.readAllBytes(), "UTF-8");
                                        Document doc = Jsoup.parse(html);
                                        htmlContent = doc.text();
                                    } catch (IOException e) { // Handle exception
                                    }
                                } else {
                                    // Handle attachment
                                }
                            }
                        } else {
                            plainContent = (String) content;
                        }
                    } catch (IOException | MessagingException e) {
                        e.printStackTrace();
                        // Handle exception
                    }
                }
            }
        }
    }

    public String extractTextContent(Message message) throws MessagingException, IOException {
        Object content = message.getContent();
        return getTextFromMessage(content);
    }

    public String getTextFromMessage(Object content) throws MessagingException, IOException {
        if (content instanceof Multipart) {
            Multipart multipart = (Multipart) content;
            StringBuilder text = new StringBuilder();
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                text.append(getTextFromMessage(bodyPart.getContent()));
            }
            return text.toString();
        } else if (content instanceof String) {
            return (String) content;
        }
        return "";
    }

    public String getPlainContent() {
        return plainContent;
    }

    public String getHTMLContent() {
        return htmlContent;
    }
}
