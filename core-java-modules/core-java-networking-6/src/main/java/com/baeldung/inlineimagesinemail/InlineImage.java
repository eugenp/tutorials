package com.baeldung.inlineimagesinemail;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import com.icegreen.greenmail.util.GreenMail;

public class InlineImage {

    private final String USERNAME = "YOUR_SMTP_USERNAME";
    private final String PASSWORD = "YOUR_SMTP_PASSWORD";
    private final String HOST = "SMTP HOST";
    private final String PORT = "SMTP PORT";

    public Properties smtpProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", HOST);
        prop.put("mail.smtp.port", PORT);
        prop.put("mail.smtp.user", USERNAME);
        prop.put("mail.smtp.password", PASSWORD);

        return prop;
    }

    public Session smtpsession(Properties props) {
        final String username = props.getProperty("mail.smtp.user");
        final String password = props.getProperty("mail.smtp.password");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        return session;
    }

    public Session smtpsession(GreenMail greenMail) {

        Session session = greenMail.getSmtp()
            .createSession();

        return session;
    }

    public void sendEmail(Session session, String to, String subject, String body, String filePath) throws MessagingException, IOException {

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("example@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);

        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(body, "text/html");

        MimeBodyPart imagePart = new MimeBodyPart();
        imagePart.attachFile(new File(filePath));
        imagePart.setContentID("<image1>");
        imagePart.setDisposition(MimeBodyPart.INLINE);

        MimeMultipart mimeMultipart = new MimeMultipart("related");
        mimeMultipart.addBodyPart(htmlPart);
        mimeMultipart.addBodyPart(imagePart);

        message.setContent(mimeMultipart);

        Transport.send(message);
    }

    public static void main(String[] args) throws MessagingException, IOException {
        InlineImage inlineImage = new InlineImage();
        Properties properties = inlineImage.smtpProperties();
        Session session = inlineImage.smtpsession(properties);

        String to = "receiver@gmail.com";
        String subject = "Baeldung";
        String body = """
             <p>Welcome to Baeldung, home of Java and its frameworks.</p>
             <img src='cid:image1'></img>
             <p> Explore and learn. </p>
            """;
        String imagePath = "src/main/resources/image/java.png";

        inlineImage.sendEmail(session, to, subject, body, imagePath);
    }
}
