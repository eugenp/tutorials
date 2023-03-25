package com.baeldung;

import com.baeldung.pojo.Student;
import com.baeldung.pojo.Subject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.util.ArrayList;
import java.util.List;

public class DeepCopyTest {

//Unit test for copy constructor
@Test
public void whenModifyingOriginalEntity_ThenCopyShouldNotChangeCopyConstructor() {
List<Subject> subjectList = new ArrayList<>();
subjectList.add(new Subject("Computer Science","101"));
subjectList.add(new Subject("Biology","102"));
subjectList.add(new Subject("Mathematics","103"));
subjectList.add(new Subject("Chemistry","104"));
Student studentA = new Student("Brandon", "123", subjectList);
Student deepCopy = new Student(studentA);

deepCopy.getSubjects().get(0).setSubjectName("New Subject");
assertNotEquals(deepCopy.getSubjects().get(0).getSubjectName(),studentA.getSubjects().get(0).getSubjectName(),"Asserting the deepCopy subject change did not reflect for studentA");
}

//Unit test for cloning
@Test
public void whenModifyingOriginalEntity_ThenCopyShouldNotChange() {
List<Subject> subjectList = new ArrayList<>();
subjectList.add(new Subject("Computer Science","101"));
subjectList.add(new Subject("Biology","102"));
subjectList.add(new Subject("Mathematics","103"));
subjectList.add(new Subject("Chemistry","104"));
Student studentA = new Student("Brandon", "123", subjectList);
Student deepCopy = (Student) studentA.clone();

deepCopy.getSubjects().get(0).setSubjectName("New Subject");
assertNotEquals(deepCopy.getSubjects().get(0).getSubjectName(),studentA.getSubjects().get(0).getSubjectName(),"Asserting the deepCopy subject change did not reflect for studentA");
}
}