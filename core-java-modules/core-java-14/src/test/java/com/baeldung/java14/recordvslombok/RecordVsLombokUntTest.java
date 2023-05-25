package com.baeldung.java14.recordvslombok;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class RecordVsLombokUntTest {

    @Test
    public void givenAColorRecord_hexStringIsCorrect() {
        var red = new ColorRecord(255, 0, 0);

        assertThat(red.getHexString()).isEqualTo("#FF0000");
    }

    @Test
    public void givenAColorValueObject_hexStringIsCorrect() {
        var red = new ColorValueObject(255, 0, 0);

        assertThat(red.getHexString()).isEqualTo("#FF0000");
    }

    @Test
    public void givenRecordWithManyAttributes_firstNameShouldBeJohn() {
        StudentRecord john = new StudentRecord("John", "Doe", null, "john@doe.com", null, null, "England", 20);

        assertThat(john.firstName()).isEqualTo("John");
    }

    @Test
    public void givenBuilderWithManyAttributes_firstNameShouldBeJohn() {
        StudentBuilder john = StudentBuilder.builder()
            .firstName("John")
            .lastName("Doe")
            .email("john@doe.com")
            .country("England")
            .age(20)
            .build();

        assertThat(john.getFirstName()).isEqualTo("John");
    }

}