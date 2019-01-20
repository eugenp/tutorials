package com.baeldung.tutorial.hexagonal.adapter;

import com.baeldung.tutorial.hexagonal.external.UIData;
import com.baeldung.tutorial.hexagonal.port.in.UserRegistration;

/**
 * User Registration data is populated in this class from UI
 */
public class UIUserRegistrationImpl implements UserRegistration {

    private static UIData uiData = new UIData();

    public boolean subscriptionPaid() {
        return uiData.isSubscriptionPaid();
    }


    public String getUserName() {
        return uiData.getUserName();
    }


    public String getUserEmail() {
        return uiData.getUserEmail();
    }

    public String getUserAddress() {
        return uiData.getUserAddress();
    }
}
