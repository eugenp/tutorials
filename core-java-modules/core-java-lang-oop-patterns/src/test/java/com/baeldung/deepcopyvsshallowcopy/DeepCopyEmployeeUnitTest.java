package com.baeldung.deepcopyvsshallowcopy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class DeepCopyEmployeeUnitTest {

    @Test
    public void whenModifyingOriginalObject_thenCopyShouldNotChange() throws CloneNotSupportedException {
        //given
        Address address = new Address("221B Baker Street", "London", "England");
        Employee originalEmployee = new Employee("Sherlock", "Holmes", 7, address);
        Employee deepCopiedEmployee = (Employee) originalEmployee.clone();

        //when
        address.setStreet("Downing St 10");

        //then
        assertThat(originalEmployee.getAddress()
            .getStreet()).isNotEqualTo(deepCopiedEmployee.getAddress()
            .getStreet());
    }
}
