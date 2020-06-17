package com.bealdung.onboarding.application.port.output;
import com.bealdung.onboarding.adapters.persistence.EmployeeEntity;
public interface FindEmployeePort {
    EmployeeEntity findEmployee(Long id);
}
