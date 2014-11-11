package org.baeldung.event;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class OnRegistrationComplete extends ApplicationEvent {
 
	public final Registration registration;
	
	public OnRegistrationComplete(Registration source) {
		super(source);
		this.registration=source;
	}

	public Registration getRegistration() {
		return registration;
	}

}
