package com.baeldung.hexagonal.framework_layer.secondary.adapters;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Service;
import com.baeldung.hexagonal.core.domain.Article;
import com.baeldung.hexagonal.framework_layer.secondary.ports.ArticleNotificationPort;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Service
public class ArticleNotificationAdapterImpl implements ArticleNotificationPort {

    Logger logger = LoggerFactory.getLogger(ArticleNotificationAdapterImpl.class);

    @Override
    public void notify(Article article) {

        Email email = new SimpleEmail();
        email.setHostName("smtp.googlemail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("username", "password"));
        email.setSSLOnConnect(true);
        email.setSubject(article.getSubject());

        try {

            email.setFrom("user@gmail.com");
            email.setMsg(article.getContent()
                .toString());
            email.addTo("foo@bar.com");

            // This sample is only to understand the basics
            // of Java Hexagonal Architecture and we not need
            // to deal with complex mail configurations.

            logger.info("Sending article via mail....");
            // email.send();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

}
