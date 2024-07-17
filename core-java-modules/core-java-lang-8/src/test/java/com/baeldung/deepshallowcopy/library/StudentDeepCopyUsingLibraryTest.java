package com.baeldung.deepshallowcopy.library;

import com.google.gson.Gson;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StudentDeepCopyUsingLibraryTest {
    @Test
    public void whenDeepCopyingThenObjectsShouldNotBeTheSame() {

        Address address = new Address("1st", "CHN", "TN");
        Student student = new Student("Alex", "2021", "Mech", address);
        Gson gson = new Gson();
        Student newDeepCopiedStudent = gson.fromJson(gson.toJson(student), Student.class);
        assertThat(newDeepCopiedStudent)
                .isNotSameAs(student);
    }
    @Test
    public void whenDeepCopyingThenNestedObjectShouldNotBeTheSame() {

        Address address = new Address("1st", "CHN", "TN");
        Student student = new Student("Alex", "2021", "Mech", address);
        Gson gson = new Gson();
        Student newDeepCopiedStudent = gson.fromJson(gson.toJson(student), Student.class);
        assertThat(newDeepCopiedStudent.getAddress())
                .isNotSameAs(student.getAddress());
    }
    @Test
    public void whenModifyingDeepCopiedObjectThenOriginalObjectShouldNotChange() {

        Address address = new Address("1st", "CHN", "TN");
        Student student = new Student("Alex", "2021", "Mech", address);
        Gson gson = new Gson();
        Student newDeepCopiedStudent = gson.fromJson(gson.toJson(student), Student.class);
        newDeepCopiedStudent.setName("kumar");
        assertThat(newDeepCopiedStudent.getName())
                .isNotSameAs(student.getName());
    }

    @Test
    public void whenModifyingTheDeepCopyNestedObjectThenOriginalNestedObjectShouldNotChange() {

        Address address = new Address("1st", "CHN", "TN");
        Student student = new Student("Alex", "2021", "Mech", address);
        Gson gson = new Gson();
        Student newDeepCopiedStudent = gson.fromJson(gson.toJson(student), Student.class);
        newDeepCopiedStudent.getAddress().setCity("BLR");
        assertThat(newDeepCopiedStudent.getAddress().getCity())
                .isNotSameAs(student.getAddress().getCity());
    }
}
