package com.baeldung.hexagonalarchitecture.adapter;

import com.baeldung.hexagonalarchitecture.domain.Order;
import com.baeldung.hexagonalarchitecture.port.OrderStatusChangedNotifier;

public class EmailService implements OrderStatusChangedNotifier {

    private MailSender mailSender;

    public EmailService(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void orderStatusChanged(Order order) {
        mailSender.sendEmail(order.getCustomer().getEmail(),"email content");
    }
}
