package com.baeldung.deepshallowcopy.cloneable;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StudentCloneableDeepCopyTest {
    @Test
    public void whenDeepCopyingThenObjectsShouldNotBeTheSame() {
        Address address = new Address("1st", "CHN", "TN");
        Student student = new Student("Alex", "2021", "Mech", address);
        Student newDeepCopiedStudent = student.clone();
        assertThat(newDeepCopiedStudent)
                .isNotSameAs(student);
    }
    @Test
    public void whenDeepCopyingThenNestedObjectShouldNotBeTheSame() {
        Address address = new Address("1st", "CHN", "TN");
        Student student = new Student("Alex", "2021", "Mech", address);
        Student newDeepCopiedStudent = student.clone();
        assertThat(newDeepCopiedStudent.getAddress())
                .isNotSameAs(student.getAddress());
    }
    @Test
    public void whenModifyingDeepCopiedObjectThenOriginalObjectShouldNotChange() {
        Address address = new Address("1st", "CHN", "TN");
        Student student = new Student("Alex", "2021", "Mech", address);
        Student newDeepCopiedStudent = student.clone();
        newDeepCopiedStudent.setName("kumar");
        assertThat(newDeepCopiedStudent.getName())
                .isNotSameAs(student.getName());
    }

    @Test
    public void whenModifyingTheDeepCopyNestedObjectThenOriginalNestedObjectShouldNotChange() {
        Address address = new Address("1st", "CHN", "TN");
        Student student = new Student("Alex", "2021", "Mech", address);
        Student newDeepCopiedStudent = student.clone();
        newDeepCopiedStudent.getAddress().setCity("BLR");
        assertThat(newDeepCopiedStudent.getAddress().getCity())
                .isNotSameAs(student.getAddress().getCity());
    }
}
