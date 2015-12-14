package org.baeldung.java.collections.sort;

import java.util.Comparator;

import org.baeldubng.java.entity.Student;

class NameComparator implements Comparator<Student> {
    @Override
    public int compare(final Student s1, final Student s2) {

        return s1.getName().compareTo(s2.getName());
    }

}