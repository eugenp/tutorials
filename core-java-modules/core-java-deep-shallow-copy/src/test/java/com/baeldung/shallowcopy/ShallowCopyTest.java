package com.baeldung.shallowcopy;

import com.baeldung.model.College;
import com.baeldung.model.Student;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ShallowCopyTest {

    @Test
    public void shallowCopyOfAnObject() {
        College college = new College("Harvard", "harvard@cs.com");
        Student student = new Student("Bob", "Dylan", college);

        Student studentShallowCopy = new Student(
                student.getFirstName(), student.getLastName(), student.getCollege());

        assertThat(studentShallowCopy).isNotSameAs(student);
        // college reference is same as original object college reference
        assertThat(studentShallowCopy.getCollege()).isSameAs(student.getCollege());
    }

    @Test
    public void shallowCopyOfAnObject_byChangingReferenceObject() {
        College college = new College("Harvard", "harvard@cs.com");
        Student student = new Student("Bob", "Dylan", college);

        Student studentShallowCopy = new Student(
                student.getFirstName(), student.getLastName(), student.getCollege());
        // changed reference object in original object
        student.getCollege().setName("Changed");

        assertThat(studentShallowCopy).isNotSameAs(student);
        // college reference is still same as original object college reference
        assertThat(studentShallowCopy.getCollege()).isSameAs(student.getCollege());
    }
}
