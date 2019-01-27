package com.baeldung.hexagonal.ports;

import java.util.Properties;

import javax.mail.Message;

import com.baeldung.hexagonal.core.CustomMessage;
import com.baeldung.hexagonal.core.Customer;

public interface INotification {
    public boolean sendNotificatin(Properties properties, Customer customer, CustomMessage customMessages);
}
