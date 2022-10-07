package com.baeldung.shallowDeepCopyExamples;

import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import com.baeldung.shallowDeepCopyExamples.pojo.Employee;
import com.baeldung.shallowDeepCopyExamples.pojo.EmployerDetails;

public class DeepCopyWithConstructorTest {

    DeepCopyWithConstructor deepCopyWithConstructor = new DeepCopyWithConstructor();

    @Test
    public void whenCopyWithConstructor_thenDeepCopyIsCreated(){
        Employee originalEmployee = getTestEmployee();
        Employee clonedEmployee = deepCopyWithConstructor.deepCopyUsingConstructor(originalEmployee);
        assertNotEquals(originalEmployee, clonedEmployee);
        assertNotEquals(originalEmployee.getLastName(), clonedEmployee.getLastName());
        assertNotEquals(originalEmployee.getEmployerDetails().getEmployerName(), clonedEmployee.getEmployerDetails().getEmployerName());
    }

    private Employee getTestEmployee() {
        return Employee.builder()
                        .firstName("Peter")
                        .lastName("Parker")
                        .age(20)
                        .employerDetails(EmployerDetails.builder().employerName("Marvel Studios").build())
                        .build();
    }
}