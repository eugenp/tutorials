package com.bealdung.onboarding.application.port.output;

import com.bealdung.onboarding.adapters.persistence.EmployeeEntity;
import org.springframework.stereotype.Component;

@Component
public interface FindEmployeeInterface {
    EmployeeEntity findEmployee(Long id);
}
