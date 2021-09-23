package com.baeldung.design.hex.adapter.driven;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.design.hex.adapter.driven.component.SMSSenderAPI;
import com.baeldung.design.hex.port.out.IMessageSender;

@Component("smsSender")
public class SMSSender implements IMessageSender {

	@Autowired
	SMSSenderAPI smsAPI;

	@Override
	public void send(String orderId) {
		String message = "Order with orderId " + orderId + "placed successfully."
				+ "SMS ORDER <orderId> to 111 to know status";
		smsAPI.sendSMS(message);
	}

}
