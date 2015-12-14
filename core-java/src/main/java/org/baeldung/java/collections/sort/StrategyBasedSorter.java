package org.baeldung.java.collections.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.baeldubng.java.entity.Student;

public class StrategyBasedSorter {
    Student s1, s2, s3, s4, s5, s6, s7, s8;
    private List<Student> studentList;
    private Set<Student> studentSet;
    private List<Comparator<Student>> studentComparatorList;
    private StudentchainedComparator chainedComparator;

    public static void main(final String[] args) {
        // new StrategyBasedSorter().sortStudentList();
    }

    public void sortStudents(final List<? extends Student> list) {
        studentComparatorList = new ArrayList<>();
        chainedComparator = new StudentchainedComparator();
        studentComparatorList.add(new IdComparator());
        studentComparatorList.add(new NameComparator());
        System.out.println(" ************Prior to sorting ******************");
        print(list);
        // Collections.sort(list, new IdComparator());
        //
        // System.out.println(" ************After sorting with id ******************");
        // print(list);
        // System.out.println(" ************Before sorting with name ******************");
        // Collections.sort(list, new NameComparator());
        // print(list);
        System.out.println(" ************After sorting ******************");
        chainedComparator.setComparatorlist(studentComparatorList);
        Collections.sort(list, chainedComparator);
        print(list);

    }

    protected void print(final Collection<? extends Student> list) {
        for (final Student s : list) {
            System.out.println(" For each printing::" + s.getId() + "--" + s.getName());
        }
    }


}
