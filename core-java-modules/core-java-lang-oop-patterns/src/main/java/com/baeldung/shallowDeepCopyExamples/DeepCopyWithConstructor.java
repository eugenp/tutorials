package com.baeldung.shallowDeepCopyExamples;

import com.baeldung.shallowDeepCopyExamples.pojo.Employee;
import com.baeldung.shallowDeepCopyExamples.pojo.EmployerDetails;

public class DeepCopyWithConstructor {

    public Employee deepCopyUsingConstructor(Employee originalEmployee) {
        Employee clonedEmployee = new Employee(originalEmployee.getFirstName(), originalEmployee.getLastName(), originalEmployee.getAge(),
                new EmployerDetails("Cartoon Network"));
        clonedEmployee.setFirstName("Mickey");
        clonedEmployee.setLastName("Mouse");
        return clonedEmployee;
    }
}
