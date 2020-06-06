package com.baeldung.eclipsecollections;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.list.mutable.FastList;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class CollectPatternUnitTest {

    @Test
    public void whenCollect_thenCorrect() {
        Student student1 = new Student("John", "Hopkins");
        Student student2 = new Student("George", "Adams");

        MutableList<Student> students = FastList.newListWith(student1, student2);

        MutableList<String> lastNames = students.collect(Student::getLastName);

        Assertions.assertThat(lastNames).containsExactly("Hopkins", "Adams");
    }
}
