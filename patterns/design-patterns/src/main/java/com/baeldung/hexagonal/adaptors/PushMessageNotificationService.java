package com.baeldung.hexagonal.adaptors;

import java.util.Properties;

import com.baeldung.hexagonal.core.CustomMessage;
import com.baeldung.hexagonal.core.Customer;
import com.baeldung.hexagonal.ports.INotification;

public class PushMessageNotificationService implements INotification {


    public boolean sendNotificatin(Properties properties, Customer customer, CustomMessage customMessages) {
        //code to push data using Google API
        return Boolean.TRUE;
    }

}
