package com.baeldung.shallowvsdeepcopy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class DeepCopyUnitTest {

	@Test
	public void whenModifyingOriginalObject_thenCopyShouldNotChange() {
		Person originalPerson = new Person("Sam");
		Person copiedPerson = new Person(originalPerson);
		copiedPerson.setName("Jane");
		assertEquals("Sam", originalPerson.getName());
		assertEquals("Jane", copiedPerson.getName());
	}
}
