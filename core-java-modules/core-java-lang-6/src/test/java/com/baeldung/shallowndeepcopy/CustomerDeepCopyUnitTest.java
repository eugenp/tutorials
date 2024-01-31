package com.baeldung.shallowndeepcopy;

import static org.junit.Assert.*;

import org.junit.Test;

public class CustomerDeepCopyUnitTest {

	@Test
	public void whenDeepCopyObject_thenIndependentReferenceObjectCopyCreated() {
		AddressDeepCopy addressDeepCopy = new AddressDeepCopy("Front Street", 600012, "Kerala", "Trivendram");
		CustomerDeepCopy customerOriginal = new CustomerDeepCopy(1, addressDeepCopy, "John Doe", "Subscriber");

		CustomerDeepCopy customerCopy = null;

		try {
			customerCopy = (CustomerDeepCopy) customerOriginal.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		assertEquals(customerOriginal.addressDeepCopy.city, customerCopy.addressDeepCopy.city);

		customerCopy.addressDeepCopy.city = "Kozikode";

		assertNotEquals(customerOriginal.addressDeepCopy.city, customerCopy.addressDeepCopy.city);
	}

}
