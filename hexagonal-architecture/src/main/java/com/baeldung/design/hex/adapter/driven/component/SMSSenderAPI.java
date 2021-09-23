package com.baeldung.design.hex.adapter.driven.component;

import org.springframework.stereotype.Component;

@Component
public class SMSSenderAPI {
	public void sendSMS(String message) {
		System.out.println(message);
	}
}
