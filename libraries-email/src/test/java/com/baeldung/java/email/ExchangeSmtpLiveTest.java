package com.baeldung.java.email;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Properties;

import org.junit.jupiter.api.Test;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class ExchangeSmtpLiveTest {

    private static final String SMTP_HOST = "smtp.office365.com";
    private static final String SMTP_PORT = "587";

    // Replace placeholders before running
    private static final String VALID_USERNAME = "<EXCHANGE_USERNAME>";
    private static final String VALID_PASSWORD = "<EXCHANGE_PASSWORD>";

    private static final String INVALID_PASSWORD = "<INVALID_PASSWORD>";

    private static final String FROM_ADDRESS = "<SENDER_EMAIL>";
    private static final String TO_ADDRESS = "<RECEIVER_EMAIL>";

    @Test
    void givenValidCredentials_whenSendingEmail_thenMessageIsAcceptedByExchange() {

        boolean emailSent = false;
        Exception thrownException = null;

        try {
            sendEmail(SMTP_HOST, VALID_USERNAME, VALID_PASSWORD, TO_ADDRESS, "SMTP Success Test", "Email sent using valid Exchange credentials.");
            emailSent = true;
        } catch (Exception ex) {
            thrownException = ex;
        }

        assertTrue(emailSent, "Expected email to be sent successfully");
        assertNull(thrownException, "Did not expect any exception");
    }

    @Test
    void givenInvalidPassword_whenSendingEmail_thenAuthenticationIsRejected() {

        boolean emailSent = false;
        MessagingException thrownException = null;

        try {
            sendEmail(SMTP_HOST, VALID_USERNAME, INVALID_PASSWORD, TO_ADDRESS, "SMTP Auth Failure Test", "This email should not be sent.");
            emailSent = true;
        } catch (MessagingException ex) {
            thrownException = ex;
        }

        assertFalse(emailSent, "Email should not be sent with invalid password");
        assertNotNull(thrownException, "Expected authentication exception");
        assertTrue(thrownException.getMessage()
            .toLowerCase()
            .contains("auth"), "Expected authentication related error");
    }

    @Test
    void givenInvalidRecipient_whenSendingEmail_thenRecipientIsRejectedByServer() {

        boolean emailSent = false;
        MessagingException thrownException = null;

        try {
            sendEmail(SMTP_HOST, VALID_USERNAME, VALID_PASSWORD, "invalid-email-address", "Invalid Recipient Test",
                "This should fail due to invalid recipient.");
            emailSent = true;
        } catch (MessagingException ex) {
            thrownException = ex;
        }

        assertFalse(emailSent, "Email should not be sent to invalid recipient");
        assertNotNull(thrownException, "Expected recipient rejection exception");
        assertTrue(thrownException.getMessage()
            .toLowerCase()
            .contains("recipient"), "Expected recipient related error");
    }

    @Test
    void givenInvalidSmtpHost_whenSendingEmail_thenConnectionFails() {

        boolean emailSent = false;
        MessagingException thrownException = null;

        try {
            sendEmail("invalid.smtp.host", VALID_USERNAME, VALID_PASSWORD, TO_ADDRESS, "Connection Failure Test", "This should fail due to invalid SMTP host.");
            emailSent = true;
        } catch (MessagingException ex) {
            thrownException = ex;
        }

        assertFalse(emailSent, "Email should not be sent when SMTP host is invalid");
        assertNotNull(thrownException, "Expected connection failure exception");
        assertTrue(thrownException.getMessage()
            .toLowerCase()
            .contains("connect"), "Expected connection related error");
    }

    // --------------------------------------------------
    // Helper method used by tests
    // --------------------------------------------------

    private void sendEmail(String smtpHost, String username, String password, String recipient, String subject, String body) throws MessagingException {

        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth.mechanisms", "LOGIN");

        Session session = Session.getInstance(props, new ExchangeAuthenticator(username, password));

        session.setDebug(true);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM_ADDRESS));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }

    // --------------------------------------------------
    // Explicit Authenticator (no lambda)
    // --------------------------------------------------

    private static class ExchangeAuthenticator extends Authenticator {

        private final String username;
        private final String password;

        ExchangeAuthenticator(String username, String password) {
            this.username = username;
            this.password = password;
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    }
}
