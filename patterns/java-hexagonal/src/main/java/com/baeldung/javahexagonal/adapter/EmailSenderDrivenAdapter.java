package com.baeldung.javahexagonal.adapter;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.baeldung.javahexagonal.core.domain.User;
import com.baeldung.javahexagonal.core.port.EmailSenderOutputPort;

import javax.mail.Transport;

@Component
public class EmailSenderDrivenAdapter implements EmailSenderOutputPort {
	private static final Logger logger = LoggerFactory.getLogger(EmailSenderDrivenAdapter.class);

	@Override
	public void sendRegisterEmail(User user) {
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", "smtp.mailtrap.io");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.store.protocol", "pop3");
		props.put("mail.transport.protocol", "smtp");

		final String username = ""; // Add your credentials
		final String password = ""; // Add your credentials

		try {
			Session session = Session.getDefaultInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			Message message = new MimeMessage(session);
			InternetAddress from = new InternetAddress("noreply@example.com", "Baeldung");
			message.setFrom(from);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail(), user.getFirstName()));
			message.setSubject("Thanks for joining");

			String msg = "You have been registred successfully";

			message.setContent(msg, "text/html");
			Transport.send(message);

		} catch (MessagingException | IOException e) {
			logger.debug("Failed to send an mail to: {}, ex: {}", user.getEmail(), e);
		}
	}

}
