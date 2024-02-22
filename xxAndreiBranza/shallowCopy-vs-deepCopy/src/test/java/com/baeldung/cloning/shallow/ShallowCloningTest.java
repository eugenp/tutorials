package com.baeldung.cloning.shallow;

import org.junit.Test;
import static org.junit.Assert.*;

public class ShallowCloningTest {

	Person alex = new Person("Alex", "Jones", new Address("Main Street", "Main City"));

	@Test
	public void whenUsingShallowCopy_ThenReferencesAreTheSame()
	{
        Person shallowCopyOfAlex = alex.clone();

		assertEquals(alex, shallowCopyOfAlex);

		alex.getAddress().setCityName("Unknown City");

		assertEquals(alex.getAddress(), shallowCopyOfAlex.getAddress());
	}
}
