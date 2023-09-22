package com.baeldung.shallowdeepcopy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ShallowDeepCopyExampleTest {
	
    @Test 
    public void whenPerformShallowCopy_thenObjectsNotSame() { 
        School school = new School("Baeldung School");
        Student originalStudent = new Student("John", 20, school); 
        Student shallowCopy = new Student(originalStudent.getName(), originalStudent.getAge(), originalStudent.getSchool());
        assertNotSame(shallowCopy, originalStudent); 
    }

    @Test public void whenModifyingActualObject_thenCopyAlsoChange() { 
        School school = new School("Baeldung School"); 
        Student originalStudent = new Student("John", 20, school); 
        Student shallowCopy = new Student(originalStudent.getName(), originalStudent.getAge(), originalStudent.getSchool()); 
        school.setSchoolName("New Baeldung School"); 
        assertEquals(shallowCopy.getSchool().getSchoolName(), originalStudent.getSchool().getSchoolName());
    }

	@Test
    public void whenModifyingActualObject_thenCloneCopyNotChange() { 
        School school = new School("New School");
        Student originalStudent = new Student("Alice", 10, school); 
        Student deepCopy = (Student) originalStudent.clone(); 
        school.setSchoolName("New Baeldung School"); 
        assertNotEquals(deepCopy.getSchool().getSchoolName(), originalStudent.getSchool().getSchoolName()); 
    }

    @Test 
    public void whenModifyingActualObject_thenCopyNotChange() {
        School school = new School("Baeldung School");
        Student originalStudent = new Student("Alice", 30, school); 
        Student deepCopy = new Student(originalStudent); 
        school.setSchoolName("New Baeldung School"); 
        assertNotEquals( originalStudent.getSchool().getSchoolName(), deepCopy.getSchool().getSchoolName()); 
    }
}
