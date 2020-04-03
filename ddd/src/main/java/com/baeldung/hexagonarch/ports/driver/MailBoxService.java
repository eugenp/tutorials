package com.baeldung.hexagonarch.ports.driver;

public interface MailBoxService {
    void sendFlyer(String streetName, String city, String zipCode);
}