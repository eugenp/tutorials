package com.baeldung.spring.kafka.deserialization.exception;

import java.util.logging.Logger;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final static Logger log = Logger.getLogger(EmailService.class.getName());

    public void sendNewsletter(Article article) {
        log.info("Sending newsletter for article: " + article);
    }
}
