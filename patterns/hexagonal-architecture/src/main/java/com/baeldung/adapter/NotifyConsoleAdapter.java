package com.baeldung.adapter;

import com.baeldung.port.NotifyPort;

public class NotifyConsoleAdapter implements NotifyPort {

	@Override
	public void notify(String subject, String message) {
		System.out.println(subject);
		System.out.println(message);		
	}

}
