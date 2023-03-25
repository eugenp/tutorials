package com.baeldung;

import com.baeldung.pojo.Student;
import com.baeldung.pojo.Subject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

public class ShallowCopyTest {

@Test
public void whenshallowCopy_thenEntitiesNotSame() {
List<Subject> subjectList = new ArrayList<>();
subjectList.add(new Subject("Computer Science","101"));
subjectList.add(new Subject("Biology","102"));
subjectList.add(new Subject("Mathematics","103"));
subjectList.add(new Subject("Chemistry","104"));
Student studentA = new Student("Brandon", "123", subjectList);
Student shallowCopy = new Student(studentA.getStudentName(), studentA.getRollNumber(), studentA.getSubjects());
assertEquals(shallowCopy,studentA,"Both the object are similar");
assertNotEquals(System.identityHashCode(shallowCopy),System.identityHashCode(studentA),"Both the object and copy have different address");
}

@Test
public void whenModifyingOriginalEntity_ThenCopyShouldChange() {

List<Subject> subjectList = new ArrayList<>();
subjectList.add(new Subject("Computer Science","101"));
subjectList.add(new Subject("Biology","102"));
subjectList.add(new Subject("Mathematics","103"));
subjectList.add(new Subject("Chemistry","104"));

Student studentA = new Student("Brandon", "123", subjectList);

Student shallowCopy = new Student(
studentA.getStudentName(), studentA.getRollNumber(), studentA.getSubjects());

shallowCopy.getSubjects().get(0).setSubjectName("New Subject");

//checking if the student Name is changed across both the shallow copy and original entity
assertEquals(shallowCopy.getSubjects().get(0).getSubjectName(),studentA.getSubjects().get(0).getSubjectName());
}
}