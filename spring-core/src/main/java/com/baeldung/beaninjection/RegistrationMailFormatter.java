package com.baeldung.beaninjection;

import java.util.Map;

public interface RegistrationMailFormatter {
    String format(Map<String, String> context);
}
