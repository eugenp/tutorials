package org.baeldung.endpoints;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.stereotype.Component;

@Component
class CustomEndpoint implements Endpoint<List<String>> {

    public CustomEndpoint() {

    }

    public String getId() {
        return "customEndpoint";
    }

    public boolean isEnabled() {
        return true;
    }

    public boolean isSensitive() {
        return true;
    }

    public List<String> invoke() {
        // Your logic to display the output
        List<String> messages = new ArrayList<>();
        messages.add("This is message 1");
        messages.add("This is message 2");
        return messages;
    }
}
