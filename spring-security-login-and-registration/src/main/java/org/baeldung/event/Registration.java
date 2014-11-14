package org.baeldung.event;

import java.util.Locale;
import org.baeldung.persistence.model.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class Registration implements ApplicationEventPublisherAware {

	private ApplicationEventPublisher eventPublisher;

	public String getAppUrl() {
		return appUrl;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	private String appUrl;
	private Locale locale;
	private User user;

	public Registration() {
		super();
	}

	public void deliver() {
		this.eventPublisher.publishEvent(new OnRegistrationComplete(this));
	}

	@Override
	public void setApplicationEventPublisher(
			ApplicationEventPublisher applicationEventPublisher) {
		this.eventPublisher = applicationEventPublisher;

	}

}
