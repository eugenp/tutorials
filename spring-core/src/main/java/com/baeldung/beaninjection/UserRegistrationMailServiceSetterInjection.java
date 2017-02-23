package com.baeldung.beaninjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserRegistrationMailServiceSetterInjection {

    private static final String REGISTRATION_MAIL_SUBJECT = "Welcome to Wonderful Inc.";

    private final MailSender mailSender;
    private RegistrationMailFormatter registrationMailFormatter = new PlainTextMailFormatter();

    @Autowired
    public UserRegistrationMailServiceSetterInjection(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Autowired(required = false)
    public void setRegistrationMailFormatter(RegistrationMailFormatter registrationMailFormatter) {
        this.registrationMailFormatter = registrationMailFormatter;
    }

    public void sendRegistrationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@example.com");
        message.setTo(user.getEmail());
        message.setSubject(REGISTRATION_MAIL_SUBJECT);
        message.setText(registrationMailFormatter.format(getMailTextParams(user)));
        mailSender.send(message);
    }


    private static Map<String, String> getMailTextParams(User user) {
        Map<String, String> params = new HashMap<>();
        params.put("username", user.getUsername());
        params.put("password", user.getPassword());
        return Collections.unmodifiableMap(params);
    }

}
