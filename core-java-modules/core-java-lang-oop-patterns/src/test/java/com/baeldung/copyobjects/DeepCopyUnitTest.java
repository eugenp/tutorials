package com.baeldung.copyobjects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

public class DeepCopyUnitTest {

	@Test
	public void testDeepCopy() {
		Address address = new Address("123", "Main Street", "Pune", "CA");
		Student original = new Student(1, "Dave", address);
		Student deepCopy = new Student(original);
		assertEquals(original.getName(), deepCopy.getName());
		// Ensure that the address object is not shared between original and
		// copy
		assertNotEquals(original.getAddress().hashCode(), deepCopy.getAddress()
				.hashCode());
		original.getAddress().setHouseNum("456");
		original.getAddress().setStreet("Second St");
		// Ensure that changes to the original address do not affect the copy
		assertEquals("123", deepCopy.getAddress().getHouseNum());
		assertEquals("Main Street", deepCopy.getAddress().getStreet());
	}
}
