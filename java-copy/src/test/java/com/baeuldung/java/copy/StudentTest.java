package com.baeuldung.java.copy;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class StudentTest {

  @Test
  void testShallowCopy() throws CloneNotSupportedException {
    Student student1 = new Student("John", "Math");
    Student student2 = (Student) student1.clone();
    assertNotSame(student1, student2);
    assertSame(student1.course, student2.course); // References are same
  }

  @Test
  void testDeepCopy() throws CloneNotSupportedException {
    Student student1 = new Student("John", "Math");
    Student student3 = (Student) student1.deepClone();
    assertNotSame(student1, student3);
    assertNotSame(student1.course, student3.course); // References are different
  }
}
