package com.baeldung.mockito.argumentcaptor;

public interface DeliveryPlatform {

    void send(Email email);

    String getServiceStatus();

    AuthenticationStatus authenticate(Credentials credentials);
}
