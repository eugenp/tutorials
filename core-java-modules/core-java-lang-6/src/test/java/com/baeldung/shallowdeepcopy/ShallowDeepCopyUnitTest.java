package com.baeldung.shallowdeepcopy;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.jupiter.api.Test;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ShallowDeepCopyUnitTest {

    @Test
    public void whenModifyingOriginalObject_thenCopyShouldChange() throws Exception {
        Student student1 = new Student();
        student1.setName("John Doe");

        Student student2 = (Student) student1.clone();
        assertEquals(student1.getName(), student2.getName());

        student1.getCourses().add("Math");

        assertEquals(student1.getCourses(), student2.getCourses());
    }


    @Test
    public void whenModifyingOriginalObject_thenSerializedCopyShouldNotChange() throws Exception {
        Student student = new Student();
        student.setName("John");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(student);
        out.flush();
        out.close();

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream in = new ObjectInputStream(bis);
        Student deepCopy = (Student) in.readObject();
        in.close();
        deepCopy.getCourses().add("Math");

        assertEquals(new ArrayList<String>(), student.getCourses());
        System.out.println(student.getCourses());
    }

    @Test

    public void whenModifyingOriginalObject_thenCopyConstructedObjectShouldNotChange() throws Exception {
        Student student1 = new Student();
        student1.setName("John");
        Student student2 = new Student(student1);

        assertEquals(student1.getCourses(), student2.getCourses());

        student1.getCourses().add("History");

        assertNotEquals(student1.getCourses(), student2.getCourses());
    }

    @Test
    public void whenModifyingOriginalObject_thenJacksonsCloneShouldNotChange() throws Exception {
        Student student1 = new Student();
        student1.setName("John");
        ObjectMapper objectMapper = new ObjectMapper();
        Student student2 = objectMapper.readValue(objectMapper.writeValueAsString(student1), Student.class);
        student1.getCourses().add("History");

        assertNotEquals(student1.getCourses(), student2.getCourses());
    }
}