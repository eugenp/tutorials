package com.baeldung.copyobjects;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ShallowCopyUnitTest {

	@Test
	public void testShallowCopy() {
		Address address = new Address("123", "Main Street", "Pune", "CA");
		Employee original = new Employee("Dave", address);
		Employee shallowCopy = new Employee(original);
		assertEquals(original.getName(), shallowCopy.getName());
		assertEquals(original.getAddress(), shallowCopy.getAddress());
		// Ensure that the address object is shared between original and copy		
		original.getAddress().setHouseNum("456");
		original.getAddress().setStreet("Second St");
		assertEquals(original.getAddress().hashCode(), shallowCopy.getAddress()
				.hashCode());
		assertEquals("456", shallowCopy.getAddress().getHouseNum());
		assertEquals("456", original.getAddress().getHouseNum());
		assertEquals("Second St", shallowCopy.getAddress().getStreet());
		assertEquals("Second St", original.getAddress().getStreet());

	}
}
