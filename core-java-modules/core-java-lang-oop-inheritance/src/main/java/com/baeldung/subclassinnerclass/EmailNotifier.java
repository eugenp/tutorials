package com.baeldung.subclassinnerclass;

public class EmailNotifier extends Notifier {
    @Override
    void notify(Message e) {
        // connect to the email connector and send email
        EmailConnector emailConnector = new EmailConnector();
        emailConnector.publish(e);
    }

    // Inner class for email connection
    static class EmailConnector {
        private String emailHost;
        private int emailPort;
        // Getter Setters

        private void publish(Message e) {
            // connect to the smtp server
        }
    }
}
