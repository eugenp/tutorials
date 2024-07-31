package com.baeldung.subclassinnerclass;

public class EmailNotifier extends Notifier {
    @Override
    void notify(Message e) {
        // connect to the email connector
        EmailConnector emailConnector = new EmailConnector();
        emailConnector.connect();
        // send email
    }

    // Inner class for email connection
    static class EmailConnector {
        private String emailHost;
        private int emailPort;
        // Getter Setters

        private void connect() {
            // connect to the smtp server
        }
    }
}
