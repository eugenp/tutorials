package org.baeldung.java.collections.sort;

import java.util.Comparator;

import org.baeldung.java.entity.Student;

class IdComparator implements Comparator<Student> {

    @Override
    public int compare(final Student s1, final Student s2) {

        return s1.getId() > s2.getId() ? 1 : s1.getId() == s2.getId() ? 0 : -1;
    }

}
