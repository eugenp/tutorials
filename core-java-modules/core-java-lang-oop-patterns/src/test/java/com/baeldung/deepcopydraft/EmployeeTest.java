package com.baeldung.deepcopydraft;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Test;

class EmployeeTest {

    // Shallow Copy Tests
    @Disabled
    @Test
    void givenShallowCopyCreatedUsingConstructor_whenModifyingOriginalObject_thenCopyShouldChange() {

        Company company = new Company("John Ventures");
        Employee originalObject = new Employee(1, "John", company);
        Employee shallowCopy = new Employee(originalObject.getId(), originalObject.getName(), originalObject.getCompany());

        originalObject.getCompany().setName("New Company");

        assertThat(shallowCopy.getCompany().getName()).isEqualTo("New Company");
    }

    @Test
    void whenModifyingOriginalObject_thenCopyPrimitiveShouldNotChange() {

        Company company = new Company("John Ventures");
        Employee originalObject = new Employee(1, "John", company);
        Employee shallowCopy = new Employee(originalObject.getId(), originalObject.getName(), originalObject.getCompany());

        originalObject.setId(10);

        assertThat(shallowCopy.getId()).isEqualTo(1);
    }

    // Deep Copy Tests
    @Test
    void givenDeepCopyCreatedUsingCopyConstructorwhenModifyingOriginalObject_thenCopyShouldNotChange() {

        Company company = new Company("John Ventures");
        Employee originalObject = new Employee(1, "John", company);
        Employee deepCopy = new Employee(originalObject.getId(), originalObject.getName(), originalObject.getCompany());

        originalObject.getCompany().setName("New Company");

        assertThat(deepCopy.getCompany().getName()).isEqualTo("John Ventures");
    }

    @Test
    void givenDeepCopyCreatedUsingCloneable_whenModifyingOriginalObject_thenCopyShouldNotChange() {

        Company company = new Company("John Ventures");
        Employee originalObject = new Employee(1, "John", company);
        Employee deepCopy = originalObject.clone();

        originalObject.getCompany().setName("New Company");

        assertThat(deepCopy.getCompany().getName()).isEqualTo("John Ventures");
    }

    @Test
    void givenDeepCopyCreatedUsingSerialization_whenModifyingOriginalObject_thenCopyShouldNotChange() {

        Company company = new Company("John Ventures");
        Employee originalObject = new Employee(1, "John", company);
        Employee deepCopy = SerializationUtils.clone(originalObject);

        originalObject.getCompany().setName("New Company");

        assertThat(deepCopy.getCompany().getName()).isEqualTo("John Ventures");
    }
}
