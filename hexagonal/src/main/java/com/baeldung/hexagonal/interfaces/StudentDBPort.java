package com.baeldung.hexagonal.interfaces;

import com.baeldung.hexagonal.beans.Student;

public interface StudentDBPort {

    public Object createStudent(Student pStudent);

    public Object updateStudent(Student pStudent);

    public Object findById(long id);
}
