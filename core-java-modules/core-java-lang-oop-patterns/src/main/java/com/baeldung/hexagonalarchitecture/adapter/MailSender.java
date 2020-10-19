package com.baeldung.hexagonalarchitecture.adapter;

public interface MailSender {

    boolean sendEmail(String recipient, String content);

}
