package com.baeldung.hexagonal.ports;

import java.util.Map;

public interface NotificationSenderPort {

    void notify(String handle, Map<String, String> data);

}

