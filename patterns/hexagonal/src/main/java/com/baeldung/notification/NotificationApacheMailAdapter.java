package com.baeldung.notification;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.baeldung.model.Message;

public class NotificationApacheMailAdapter implements NotificationPort {

	private String hostname;
	private int port;
	private String userAuth;
	private String passwordAuth;

	public NotificationApacheMailAdapter(String hostname, int port, String userAuth, String passwordAuth) {
		super();
		this.hostname = hostname;
		this.port = port;
		this.userAuth = userAuth;
		this.passwordAuth = passwordAuth;
	}

	@Override
	public void send(Message message) {
		try {
			Email email = new SimpleEmail();
			email.setHostName(hostname);
			email.setSmtpPort(port);
			email.setSSL(true);
			email.setAuthenticator(new DefaultAuthenticator(userAuth, passwordAuth));
			email.setFrom(message.getFrom());
			email.addTo(message.getTo());
			email.setSubject(message.getSubject());
			email.setMsg(message.getContent());
			email.send();
		} catch (EmailException e) {
			Logger.getLogger(NotificationApacheMailAdapter.class.getName()).log(Level.SEVERE, "Email send has failed.",
					e);
		}
	}

}
