package com.baeldung.hexagonalarchitecture.emailing;

/**
 * @author Fact S Musingarimi
 * 23/2/2020
 * 14:50
 */
public class EmailRequest {
    private final String subject;
    private final String body;
    private final String recipient;

    private EmailRequest(String subject, String body, String recipient) {
        this.subject = subject;
        this.body = body;
        this.recipient = recipient;
    }

    public static EmailRequest of(String subject, String body, String recipient) {
        return new EmailRequest(subject, body, recipient);
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getRecipient() {
        return recipient;
    }

    @Override
    public String toString() {
        return "EmailNotificationRequest{" +
                "subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", recipient='" + recipient + '\'' +
                '}';
    }
}
