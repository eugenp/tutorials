package org.baeldung.event.service;


import java.util.UUID;
import org.baeldung.event.OnRegistrationComplete;
import org.baeldung.persistence.model.User;
import org.baeldung.persistence.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService implements
		ApplicationListener<OnRegistrationComplete> {
	@Autowired
	private IUserService service;
	@Autowired
	private MessageSource messages;

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void onApplicationEvent(OnRegistrationComplete event) {
		this.confirmRegistration(event);
	}

	private void confirmRegistration(OnRegistrationComplete event) {
		User user = event.getRegistration().getUser();
		String token = UUID.randomUUID().toString();
		service.addVerificationToken(user, token);
		String recipientAddress = user.getEmail();
		String subject = "Registration Confirmation";
		String confirmationUrl = event.getRegistration().getAppUrl()
				+ "/regitrationConfirm.html?token=" + token;
		String message = messages.getMessage("message.regSucc", null, event
				.getRegistration().getLocale());
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAddress);
		email.setSubject(subject);
		email.setText(message + " \r\n" + "http://localhost:8080"
				+ confirmationUrl);
		mailSender.send(email);

	}

}
