package com.baeldung.hexagonal.ui;

import com.baeldung.hexagonal.domain.EmployeeService;

public interface EmployeeInput {
   void collectData(EmployeeService service);
}
