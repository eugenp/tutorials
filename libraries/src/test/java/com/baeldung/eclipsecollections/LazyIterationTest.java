package com.baeldung.eclipsecollections;

import org.eclipse.collections.api.LazyIterable;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.factory.Lists;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class LazyIterationTest {

    @Test
    public void whenLazyIteration_thenCorrect() {
        Student student1 = new Student("John", "Hopkins");
        Student student2 = new Student("George", "Adams");
        Student student3 = new Student("Jennifer", "Rodriguez");

        MutableList<Student> students = Lists.mutable.with(student1, student2, student3);
        LazyIterable<Student> lazyStudents = students.asLazy();
        LazyIterable<String> lastNames = lazyStudents.collect(Student::getLastName);

        assertTrue(lastNames.anySatisfy(Predicates.equal("Hopkins")));
        assertTrue(lastNames.anySatisfy(Predicates.equal("Adams")));
        assertTrue(lastNames.anySatisfy(Predicates.equal("Rodriguez")));
    }
}
