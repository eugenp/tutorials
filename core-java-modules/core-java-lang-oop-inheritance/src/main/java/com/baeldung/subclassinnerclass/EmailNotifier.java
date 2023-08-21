package com.baeldung.subclassinnerclass;

import java.util.HashMap;

public class EmailNotifier extends Notifier {
    @Override
    void notify(Message e) {
        // Provide email specific implementation here
    }

    // Inner class for email connection
    static class EmailConnector {
        private String emailHost;
        private int emailPort;
        // Getter Setters
    }
}
