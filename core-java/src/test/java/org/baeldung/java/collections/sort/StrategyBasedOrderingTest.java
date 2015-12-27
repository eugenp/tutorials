package org.baeldung.java.collections.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.baeldung.java.entity.Student;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StrategyBasedOrderingTest {

    Student s1, s2, s3, s4, s5, s6, s7, s8;
    private List<Student> studentList;
    private Set<Student> studentSet;
    private Map<Student, String> studentMap;
    private Map<Student, String> studentLinkedMap;

    private List<Comparator<Student>> studentComparatorList;

    private Sorter sorter;

    // private StudentchainedComparator chainedComparator;

    @Before
    public void setup() {
        studentComparatorList = new ArrayList<>();
        // chainedComparator = new StudentchainedComparator();
        // studentComparatorList.add(new IdComparator());
        // studentComparatorList.add(new NameComparator());
        // chainedComparator.setComparatorlist(studentComparatorList);
        studentList = fillStudentDetails();
        studentMap = fillCourseAndStudentDetails();
        Collections.addAll(studentList, s1, s2, s7, s4, s5, s6, s3, s8);
        sorter = new Sorter();

    }

    @Test
    public void givenStudentsSet_whensortedwithIdComparator_thenOrderwithIds() {
        studentSet = new TreeSet<>(new IdComparator());
        studentSet.addAll(studentList);
        Assert.assertArrayEquals(" Students are ordered by Id", studentSet.toArray(), new Student[] { s7, s4, s6, s8, s1, s2 });
    }

    @Test
    public void givenStudentsHashSet_whensortedwithNameComparator_thenSortCorrectlyByNames() {
        studentSet = new TreeSet<>(new NameComparator());
        studentSet.addAll(studentList);
        Assert.assertArrayEquals(" Students Set is ordered by Name ", studentSet.toArray(), new Student[] { s7, s2, s4, s1, s3, s5, s6, s8 });
    }

    @Test
    public void givenStudentsList_whensortedwithIdComparator_thenOrderwithIds() {
        Collections.sort(studentList, new IdComparator());
        Assert.assertArrayEquals(" Students List is ordered by Id", studentList.toArray(), new Student[] { s7, s3, s4, s5, s6, s8, s1, s2 });
    }

    @Test
    public void givenHashMap_whenAddedToTreeMapWithIDdComparator_thenOrderCorrectlybyId() {
        final Map<Student, String> orderedStudentMap = new TreeMap<>(new IdComparator());
        orderedStudentMap.putAll(studentMap);
        final Set<Student> students = orderedStudentMap.keySet();
        Assert.assertArrayEquals(" Students Map is ordered by Id", students.toArray(), new Student[] { s7, s5, s6, s8, s1, s2 });
    }

    @Test
    public void givenStudentAndCourseIdHashMap_whensorted_thenOrderedbyId() {
        studentLinkedMap = sorter.sortStudentMapByIds(studentMap);
        final Set<Student> orderedStudentsSet = studentLinkedMap.keySet();
        Assert.assertArrayEquals(" Students Map is ordered by Id", orderedStudentsSet.toArray(), new Student[] { s7, s3, s5, s4, s6, s8, s1, s2 });
    }

    private List<Student> fillStudentDetails() {
        studentList = new ArrayList<>();
        s1 = new Student(1000, "James");
        s2 = new Student(2000, "Brad");
        s3 = new Student(70, "John");
        s4 = new Student(80, "David");
        s5 = new Student(80, "Matt");
        s6 = new Student(90, "Michael");
        s7 = new Student(70, "Anna");
        s8 = new Student(100, "Shards");
        return studentList;
    }

    private Map<Student, String> fillCourseAndStudentDetails() {
        studentMap = new HashMap<Student, String>();
        studentMap.put(s1, "Science");
        studentMap.put(s2, "Arts");
        studentMap.put(s3, "Geology");
        studentMap.put(s4, "Goology");
        studentMap.put(s5, "Science");
        studentMap.put(s6, "Commerce");
        studentMap.put(s7, "Biology");
        studentMap.put(s8, "IT");
        return studentMap;
    }

}
