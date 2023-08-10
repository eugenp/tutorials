package com.baeldung.shallowdeepcopy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ShallowDeepCopyExampleTest {

	@Test
	void testShallowCopy() {
		Student originalStudent = new Student("John", 20);
		Student shallowCopy = originalStudent;

		shallowCopy.setName("Baeldung");
		shallowCopy.setRollno(10);

		assertEquals("Baeldung", originalStudent.getName());
		assertEquals(10, originalStudent.getRollno());
	}

    @Test
    void testDeepCopy() {
        Student originalStudent = new Student("John", 20);
        Student deepCopy = new Student(originalStudent.getName(), originalStudent.getRollno());

        deepCopy.setName("Baeldung");
        deepCopy.setRollno(10);

        assertEquals("John", originalStudent.getName());
        assertEquals(20, originalStudent.getRollno());

        assertEquals("Baeldung", deepCopy.getName());
        assertEquals(10, deepCopy.getRollno());
    }
}
