package com.baeldung.deepcopyvsshallowcopy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ShallowCopyStudentUnitTest {

    @Test
    public void whenModifyingOriginalObject_thenCopyShouldChange() throws CloneNotSupportedException {
        //given
        Address address = new Address("Downing St 10", "London", "England");
        Student originalStudent = new Student(1212, 25, address);
        Student shallowCopiedStudent = (Student) originalStudent.clone();

        //when
        address.setStreet("221B Baker Street");

        //then
        assertThat(originalStudent.getAddress()).isEqualTo(shallowCopiedStudent.getAddress());
    }

}
