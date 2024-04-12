package com.baeldung.lombok.equalsandhashcode.commonissue;

import org.junit.Test;

public class EqualsAndHashCodeUnitTest {

    @Test(expected = StackOverflowError.class)
    public void whenUsingEqualsAndHashInObjectWithCircularDependency_thenCallingEqualsAndHasCodeWillThrowStackOverFlowError() {
        Manager manager = new Manager("Mannat", null);

        Employee employee = new Employee("Nimi", 15, 22, manager);

        manager.setAssistantManager(employee);

        employee.setManager(manager);

        int hash = employee.hashCode();

    }

    @Test
    public void whenUsingEqualsAndHashInObjectAndExcludingCircularDependencyField_thenNoStackOverFlowError() {
        ManagerV2 manager = new ManagerV2("Mannat", null);

        EmployeeV2 employee = new EmployeeV2("Nimi", 15, 22, manager);

        manager.setAssistantManager(employee);

        employee.setManager(manager);

        int hash = employee.hashCode();

    }
}
