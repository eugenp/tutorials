package com.baeldung.spring.data.noconverterfound;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConverterNotFoundException;

import com.baeldung.spring.data.noconverterfound.models.Employee;
import com.baeldung.spring.data.noconverterfound.repositories.EmployeeRepository;

@SpringBootTest(classes = NoConverterFoundApplication.class)
class NoConverterFoundApplicationIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Disabled("This test will not throw any exception after providing the solution")
    @Test
    void givenEmployee_whenGettingFullName_thenThrowException() {
        Employee emp = new Employee();
        emp.setId(1);
        emp.setFirstName("Adrien");
        emp.setLastName("Juguet");
        emp.setSalary(4000);

        employeeRepository.save(emp);

        assertThatThrownBy(() -> employeeRepository.findEmployeeFullNameById(1))
          .isInstanceOf(ConverterNotFoundException.class)
          .hasMessageContaining("No converter found capable of converting from type [com.baeldung.spring.data.noconverterfound.models.Employe");
    }

    @Test
    void givenEmployee_whenGettingFullNameUsingClass_thenReturnFullName() {
        Employee emp = new Employee();
        emp.setId(2);
        emp.setFirstName("Azhrioun");
        emp.setLastName("Abderrahim");
        emp.setSalary(3500);

        employeeRepository.save(emp);

        assertThat(employeeRepository.findEmployeeFullNameById(2).fullName())
          .isEqualTo("Azhrioun Abderrahim");
    }

    @Test
    void givenEmployee_whenGettingFullNameUsingInterface_thenReturnFullName() {
        Employee emp = new Employee();
        emp.setId(3);
        emp.setFirstName("Eva");
        emp.setLastName("Smith");
        emp.setSalary(3500);

        employeeRepository.save(emp);

        assertThat(employeeRepository.findIEmployeeFullNameById(3).fullName())
          .isEqualTo("Eva Smith");
    }

}
