package com.baeldung.mail;

import java.io.File;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Properties;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class EmailService {

    private String username;
    private String password;

    private final Properties prop;

    public EmailService(String host, int port, String username, String password) {
        prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);
        prop.put("mail.smtp.ssl.trust", host);

        prop.put("mail.smtp.connectiontimeout", "10000");
        prop.put("mail.smtp.timeout", "10000");
        prop.put("mail.smtp.writetimeout", "10000");

        this.username = username;
        this.password = password;
    }

    public EmailService(String host, int port) {
        prop = new Properties();
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);
    }

    public EmailService(String host, int port, boolean bypassServerCertError) throws NoSuchAlgorithmException, KeyManagementException {
        prop = new Properties();
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);
        if (bypassServerCertError) {

            prop.put("mail.smtp.ssl.trust", "*");
            prop.put("mail.smtp.ssl.checkserveridentity", false);

            // use this when SMTPS protocol
            // SSLContext sslContext = SSLContext.getInstance("TLS");
            // sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            // prop.put("mail.smtp.ssl.enable", "true");
            // prop.put("mail.smtp.ssl.socketFactory", sslSocketFactory);
            // prop.put("mail.smtp.ssl.trust", "*");
        }
    }

    TrustManager[] trustAllCerts = new TrustManager[] {
        new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException{}
            public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException{}
            public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0];}
        }
    };

    public static void main(String... args) {
        try {
            new EmailService("smtp.mailtrap.io", 25, "87ba3d9555fae8", "91cb4379af43ed").sendMail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMail() throws Exception {

        Session session = getSession();

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("from@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("to@gmail.com"));
        message.setSubject("Mail Subject");
        String msg = "This is my first email using JavaMailer";
        message.setContent(getMultipart(msg));

        Transport.send(message);
    }

    public void sendMailToMultipleRecipients() throws Exception {
        Session session = getSession();

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("from@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("to@gmail.com, to1@gmail.com"));
        message.addRecipients(Message.RecipientType.TO, InternetAddress.parse("to2@gmail.com, to3@gmail.com"));
        message.setSubject("Mail Subject");
        String msg = "This is my first email using JavaMailer";
        message.setContent(getMultipart(msg));

        Transport.send(message);
    }

    private Multipart getMultipart(String msg) throws Exception {
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

        String msgStyled = "This is my <b style='color:red;'>bold-red email</b> using JavaMailer";
        MimeBodyPart mimeBodyPartWithStyledText = new MimeBodyPart();
        mimeBodyPartWithStyledText.setContent(msgStyled, "text/html; charset=utf-8");

        MimeBodyPart attachmentBodyPart = new MimeBodyPart();

        attachmentBodyPart.attachFile(getFile());

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        multipart.addBodyPart(mimeBodyPartWithStyledText);
        multipart.addBodyPart(attachmentBodyPart);
        return multipart;
    }

    private Session getSession() {
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        return session;
    }

    private File getFile() throws Exception {
        URI uri = this.getClass()
            .getClassLoader()
            .getResource("attachment.txt")
            .toURI();
        return new File(uri);
    }

}
