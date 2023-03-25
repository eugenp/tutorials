package com.baeldung.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@ToString
public class Student implements Serializable, Cloneable{

private String studentName;
private String rollNumber;
private List<Subject> subjects;

//copy constructor for Student
public Student(Student student)
{
this(student.getStudentName(), student.getRollNumber(),student.getSubjects().stream().map((sub)->new Subject(sub.getSubjectName(),sub.getSubjectId())).collect(Collectors.toList()));
}

@Override public Object clone()
{
Student student = null;

try {
student = (Student) super.clone();
} catch (CloneNotSupportedException e) {
student = new Student( this.getStudentName(), this.getRollNumber(), this.getSubjects());
}

student.subjects = new ArrayList<Subject>();

for(int i=0; i<this.subjects.size();i++) {
student.subjects.add((Subject) this.subjects.get(i).clone());
}

return student;
}
}