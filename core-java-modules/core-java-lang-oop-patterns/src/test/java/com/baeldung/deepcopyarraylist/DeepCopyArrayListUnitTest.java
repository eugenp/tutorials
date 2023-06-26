package com.baeldung.deepcopyarraylist;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class DeepCopyArrayListUnitTest {

    @Test
    public void whenCreatingCopyWithArrayListConstructor_thenObjectsShouldBeSame() {
        Course course = new Course(1, "Spring Masterclass");
        Student student1 = new Student(1, "John", course);
        Student student2 = new Student(2, "David", course);
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);

        List<Student> shallowCopy = new ArrayList<>(students);

        Assertions.assertNotSame(students,shallowCopy);
        Assertions.assertEquals(students.get(0), shallowCopy.get(0));
        Assertions.assertEquals(students.get(1), shallowCopy.get(1));

        shallowCopy.remove(1);

        Assertions.assertNotEquals(students.size(), shallowCopy.size());

    }

    @Test
    public void whenCreatingDeepCopyWithCopyConstructor_thenObjectsShouldNotBeSame() {

        Course course = new Course(1, "Spring Masterclass");
        Student student1 = new Student(1, "John", course);
        Student student2 = new Student(2, "David", course);

        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);

        List<Student> deepCopy = Student.deepCopyUsingCopyConstructor(students);

        Assertions.assertNotEquals(students.get(0), deepCopy.get(0));
        Assertions.assertNotEquals(students.get(1), deepCopy.get(1));
    }

    @Test
    public void whenCreatingDeepCopyWithSerializationUtils_thenObjectsShouldNotBeSame() {

        Course course = new Course(1, "Spring Masterclass");
        Student student1 = new Student(1, "John", course);
        Student student2 = new Student(2, "David", course);

        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);

        List<Student> deepCopy = Student.deepCopyUsingSerialization(students);

        Assertions.assertNotEquals(students.get(0), deepCopy.get(0));
        Assertions.assertNotEquals(students.get(1), deepCopy.get(1));
    }
}
