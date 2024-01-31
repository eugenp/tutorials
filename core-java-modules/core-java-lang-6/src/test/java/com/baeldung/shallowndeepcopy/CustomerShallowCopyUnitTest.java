/**
 * 
 */
package com.baeldung.shallowndeepcopy;

import static org.junit.Assert.*;

import org.junit.Test;

public class CustomerShallowCopyUnitTest {

	@Test
	public void whenShallowCopyObject_thenIndependentReferenceObjectCopyNotCreated() {
		AddressShallowCopy addressShallowCopy = new AddressShallowCopy("Front Street", 600012, "Kerala", "Trivendram");
		CustomerShallowCopy customerOriginal = new CustomerShallowCopy(1, addressShallowCopy, "John Doe", "Subscriber");

		CustomerShallowCopy customerCopy = null;

		try {
			customerCopy = (CustomerShallowCopy) customerOriginal.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		assertEquals(customerOriginal.addressShallowCopy.city, customerCopy.addressShallowCopy.city);

		customerCopy.addressShallowCopy.city = "Kozikode";

		assertEquals(customerOriginal.addressShallowCopy.city, customerCopy.addressShallowCopy.city);
	}

}
