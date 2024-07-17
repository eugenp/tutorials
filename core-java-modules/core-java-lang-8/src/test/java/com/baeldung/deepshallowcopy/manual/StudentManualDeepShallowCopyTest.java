package com.baeldung.deepshallowcopy.manual;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentManualDeepShallowCopyTest {

    /*
            As Shallow copy both original and copied student share the same address object,
            we can see the address getting changed for both the objects.
        */
    @Test
    public void whenShallowCopyingThenObjectsShouldNotBeTheSame() {

        Address address = new Address("1st", "CHN", "TN");
        Student student = new Student("Alex", "2021", "Mech", address);
        Student newShallowCopiedStudent = student.studentShallowCopy();
        assertThat(newShallowCopiedStudent)
                .isNotSameAs(student);
    }
    @Test
    public void whenShallowCopyingThenNestedObjectShouldBeTheSame() {

        Address address = new Address("1st", "CHN", "TN");
        Student student = new Student("Alex", "2021", "Mech", address);
        Student newShallowCopiedStudent = student.studentShallowCopy();
        assertThat(newShallowCopiedStudent.getAddress())
                .isSameAs(student.getAddress());
    }
    @Test
    public void whenModifyingShallowCopiedFirstLevelObjectThenOriginalFirstLevelObjectShouldNotChange() {

        Address address = new Address("1st", "CHN", "TN");
        Student student = new Student("Alex", "2021", "Mech", address);
        Student newShallowCopiedStudent = student.studentShallowCopy();
        newShallowCopiedStudent.setName("kumar");
        assertThat(newShallowCopiedStudent.getName())
                .isNotSameAs(student.getName());
    }
    @Test
    public void whenModifyingTheShallowCopyNestedObjectThenOriginalNestedObjectShouldChange() {

        Address address = new Address("1st", "CHN", "TN");
        Student student = new Student("Alex", "2021", "Mech", address);
        Student newShallowCopiedStudent = student.studentShallowCopy();
        newShallowCopiedStudent.getAddress().setCity("BLR");
        assertThat(newShallowCopiedStudent.getAddress().getCity())
                .isSameAs(student.getAddress().getCity());
    }
    @Test
    public void whenDeepCopyingThenObjectsShouldNotBeTheSame() {

        Address address = new Address("1st", "CHN", "TN");
        Student student = new Student("Alex", "2021", "Mech", address);
        Student newDeepCopiedStudent = student.studentDeepCopy();
        assertThat(newDeepCopiedStudent)
                .isNotSameAs(student);
    }
    @Test
    public void whenDeepCopyingThenNestedObjectShouldNotBeTheSame() {

        Address address = new Address("1st", "CHN", "TN");
        Student student = new Student("Alex", "2021", "Mech", address);
        Student newDeepCopiedStudent = student.studentDeepCopy();
        assertThat(newDeepCopiedStudent.getAddress())
                .isNotSameAs(student.getAddress());
    }
    @Test
    public void whenModifyingDeepCopiedObjectThenOriginalObjectShouldNotChange() {

        Address address = new Address("1st", "CHN", "TN");
        Student student = new Student("Alex", "2021", "Mech", address);
        Student newDeepCopiedStudent = student.studentDeepCopy();
        newDeepCopiedStudent.setName("kumar");
        assertThat(newDeepCopiedStudent.getName())
                .isNotSameAs(student.getName());
    }
    @Test
    public void whenModifyingTheDeepCopyNestedObjectThenOriginalNestedObjectShouldNotChange() {

        Address address = new Address("1st", "CHN", "TN");
        Student student = new Student("Alex", "2021", "Mech", address);
        Student newDeepCopiedStudent = student.studentDeepCopy();
        newDeepCopiedStudent.getAddress().setCity("BLR");
        assertThat(newDeepCopiedStudent.getAddress().getCity())
                .isNotSameAs(student.getAddress().getCity());
    }
}
