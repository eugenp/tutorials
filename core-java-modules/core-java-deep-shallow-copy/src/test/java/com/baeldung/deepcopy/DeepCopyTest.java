package com.baeldung.deepcopy;

import com.baeldung.model.College;
import com.baeldung.model.Student;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeepCopyTest {

    @Test
    public void deepCopyByCopyConstructor() {
        College college = new College("harvard", "harvard@cs.com");
        Student student = new Student("Bob", "Dylan", college);

        Student studentDeepCopy = new Student(student);

        assertThat(studentDeepCopy).isNotSameAs(student);
        // college reference shouldn't be same as original object college reference
        assertThat(studentDeepCopy.getCollege()).isNotSameAs(student.getCollege());
    }

    @Test
    public void deepCopyByCloneMethod() throws CloneNotSupportedException {
        College college = new College("harvard", "harvard@cs.com");
        Student student = new Student("Bob", "Dylan", college);

        Student cloned = (Student) student.clone();

        assertThat(cloned).isNotSameAs(student);
        // college reference shouldn't be same as original object college reference
        assertThat(cloned.getCollege()).isNotSameAs(student.getCollege());
    }
}
