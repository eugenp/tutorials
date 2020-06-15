package com.bealdung.onboarding.application.port.input;

import org.springframework.stereotype.Component;

@Component
public interface EmployeeNameAndFamily {
    String getNameAndFamily(Long id);
}
