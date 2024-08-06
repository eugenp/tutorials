package com.baeldung.readbodymail;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import jakarta.mail.BodyPart;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.Store;

public class EmailService {

    private final Session session;
    private String latestEmailContent;

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
                                if (bodyPart.getContentType()
                                    .startsWith("text")) {
                                    latestEmailContent = (String) bodyPart.getContent();
                                } else {
                                    saveAttachment(bodyPart);
                                }
                            }
                        } else {
                            latestEmailContent = (String) content;
                        }
                    } catch (IOException | MessagingException e) {
                        System.out.println("Error reading email content: " + e.getMessage());
                    }
                }
            }
        }
    }

    public void saveAttachment(BodyPart bodyPart) throws IOException, MessagingException {
        String fileName = bodyPart.getFileName();

        // Ensure the directory exists
        File directory = new File("attachments");
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it does not exist
        }

        // Save the attachment
        try (InputStream inputStream = bodyPart.getInputStream(); FileOutputStream outputStream = new FileOutputStream(new File(directory, fileName))) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
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

    public String getLatestEmailContent() {
        return latestEmailContent;
    }
}
