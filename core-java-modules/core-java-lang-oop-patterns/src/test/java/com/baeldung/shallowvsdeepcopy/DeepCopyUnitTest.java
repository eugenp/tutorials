package com.baeldung.shallowvsdeepcopy;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class DeepCopyUnitTest {

	@Test
    public void whenModifyingOriginalObject_thenCopyShouldNotChange() {
		
		Address address = new Address("108", "Sector 7", "Los Angeles", "USA");
		Person originalPerson = new Person("John", address);

		Person copiedPerson = new Person(originalPerson);

		address.setCountry("Argentina");
		assertNotEquals(originalPerson.getAddress().getCountry(), copiedPerson.getAddress().getCountry());
	}
}
