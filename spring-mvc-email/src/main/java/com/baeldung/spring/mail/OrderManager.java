package com.baeldung.spring.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

/**
 * Created by Olga on 8/22/2016.
 */
@Component
public class OrderManager {
    @Autowired
    public EmailService emailService;

    @Value("${attachment.invoice}")
    private String invoiceAttachmentPath;

    @Autowired()
    @Qualifier("templateOrderMessage")
    public SimpleMailMessage template;

    public void placeOrder(Order order) {
        emailService.sendSimpleMessageUsingTemplate(order.getCustomerEmail(),
                "Order Confirmation",
                template,
                order.getCustomerFirstName(),
                order.getCustomerLastName());
    }
}
