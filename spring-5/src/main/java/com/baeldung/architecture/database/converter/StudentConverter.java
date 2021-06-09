package com.baeldung.architecture.database.converter;


import com.baeldung.architecture.database.Student;
import com.baeldung.architecture.model.StudentDto;

public class StudentConverter {
    public static StudentDto map(Student student) {
        return new StudentDto()
                .setId(student.getId())
                .setName(student.getName());
    }

    public static Student map(StudentDto studentDto) {
        return new Student()
                .setId(studentDto.getId())
                .setName(studentDto.getName());
    }
}
