package com.baeldung.design.hex.adapter.driven;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.design.hex.adapter.driven.component.EmailSenderAPI;
import com.baeldung.design.hex.port.out.IMessageSender;

@Component("emailSender")
public class EmailSender implements IMessageSender {

    @Autowired
    EmailSenderAPI emailAPI;

    @Override
    public void send(String orderId) {
        String message = "Order with orderId " + orderId + "placed successfully. Click here to track status";
        emailAPI.sendEmail(message);
    }

}
