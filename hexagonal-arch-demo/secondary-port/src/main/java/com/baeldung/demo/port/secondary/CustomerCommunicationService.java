package com.baeldung.demo.port.secondary;

public interface CustomerCommunicationService {
    void notifyCrm();
    int sendWelcomeEmail(String emailId);
}
