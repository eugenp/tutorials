package com.baeldung.subclassinnerclass;

public class TextMessageNotifier extends Notifier {
    @Override
    void notify(Message e) {
        // Provide text message specific implementation here
    }

    // Inner class for text message connection
    private static class SMSConnector {
        private String smsHost;
        // Getter Setters
    }
}
