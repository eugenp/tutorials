package com.baeldung.hexagonal;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.hexagonal.adapters.StudentUserInterfaceControllerAdapter;
import com.baeldung.hexagonal.entity.Student;
import com.baeldung.hexagonal.ports.StudentUserInterfacePort;

public class StudentUserInterfaceUnitTest {

    StudentUserInterfacePort studentUserInterface = new StudentUserInterfaceControllerAdapter();

    @Before
    public void setup() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreateStudent() {
        Student s1 = new Student();
        s1.setId(1);
        s1.setName("David");
        s1.setAge(13);
        Student rs1 = studentUserInterface.createStudent(s1);
        Assert.assertEquals("B", rs1.getGrade());

        Student s2 = new Student();
        s2.setId(2);
        s2.setName("John");
        s2.setAge(16);
        Student rs2 = studentUserInterface.createStudent(s2);
        Assert.assertEquals("A", rs2.getGrade());

        Assert.assertNotEquals("B", rs2.getGrade());
    }

    @Test
    public void testGetStudent() {
        Student s1 = new Student();
        s1.setId(1);
        s1.setName("David");
        s1.setAge(13);
        studentUserInterface.createStudent(s1);

        Student rs1 = studentUserInterface.getStudent(1);
        Assert.assertEquals(s1, rs1);

        Student s2 = new Student();
        s2.setId(2);
        s2.setName("John");
        s2.setAge(16);
        studentUserInterface.createStudent(s2);

        Student rs2 = studentUserInterface.getStudent(2);
        Assert.assertEquals(s2, rs2);

        Assert.assertNotEquals(s2, rs1);
    }
}
