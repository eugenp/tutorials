package com.baeldung.deepcopy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class DeepShallowCopyUnitTest {

    @Test
    public void whenFieldByFieldAssignmentOfStringFields_thenWeGetDeepCopy() {
        UKAddress address = new UKAddress("Downing St 10", "", "London", "England", "SE111AA");
        UKAddress copy = UKAddress.newInstance(address);

        assertThat(copy).isNotSameAs(address);

        copy.setPostCode("SE111AB");
        assertThat(copy.getPostCode()).isNotEqualTo(address.getPostCode()); // doesn't change state of initial object, so deep copy
    }

    @Test
    public void whenFieldByFieldAssignmentWithNonPrimitive_thenWeGetShallowCopy() {
        UKAddress address = new UKAddress("Downing St 10", "", "London", "England", "SE111AA");
        Student student = new Student("John Wick", 3, address);
        Student copy = new Student();

        copy.setFullName(student.getFullName());
        copy.setSemester(student.getSemester());
        copy.setAddress(student.getAddress());
        copy.setSemester(5);
        copy.getAddress()
            .setPostCode("SE111AB");

        assertThat(copy.getSemester()).isNotEqualTo(student.getSemester()); // state not changed for primitives
        assertThat(copy.getAddress()).isEqualTo(student.getAddress()); // but state changed for assigned non-primitive
    }

    @Test
    public void whenFieldByFieldDeepCopy_thenWeGetDeepCopy() {
        UKAddress address = new UKAddress("Downing St 10", "", "London", "England", "SE111AA");
        Student student = new Student("John Wick", 3, address);
        Student copy = Student.newInstance(student);

        copy.setSemester(5);
        copy.getAddress()
            .setPostCode("SE111AB");

        assertThat(copy.getSemester()).isNotEqualTo(student.getSemester()); // state not changed for primitives
        assertThat(copy.getAddress()
            .getPostCode()).isNotEqualTo(student.getAddress()
            .getPostCode()); // state not changed for deep cloned non-primitive
    }

    @Test
    public void givenArrayField_whenUsingArrayClone_thenWeGetShallowCopy() {
        UKAddress address = new UKAddress("Downing St 10", "", "London", "England", "SE111AA");
        Student student = new Student("John Wick", 3, address);
        Student[] activeParticipants = new Student[] { student };
        Course course = new Course("Surviving Falls", "never die falling a roof", activeParticipants);
        Course copy = new Course();

        copy.setTitle(course.getTitle());
        copy.setDescription(course.getDescription());
        copy.setActiveParticipants(course.getActiveParticipants()
            .clone());
        copy.getActiveParticipants()[0].getAddress()
            .setPostCode("SE111AB");

        assertThat(copy.getActiveParticipants()[0].getAddress()
            .getPostCode()).isEqualTo(course.getActiveParticipants()[0].getAddress()
            .getPostCode()); // changed the state of initial object
    }

    @Test
    public void givenArrayField_whenUsingArrayDeepCopy_thenWeGetDeepCopy() {
        UKAddress address = new UKAddress("Downing St 10", "", "London", "England", "SE111AA");
        Student student = new Student("John Wick", 3, address);
        Student[] activeParticipants = new Student[] { student };
        Course course = new Course("Surviving Falls", "never die falling a roof", activeParticipants);
        Course copy = Course.newInstance(course);

        copy.getActiveParticipants()[0].getAddress()
            .setPostCode("SE111AB");

        assertThat(copy.getActiveParticipants()[0].getAddress()
            .getPostCode()).isNotEqualTo(course.getActiveParticipants()[0].getAddress()
            .getPostCode()); // doesn't change the state of initial object
    }
}
