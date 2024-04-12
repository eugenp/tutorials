package com.baeldung.protobuf;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.baeldung.protobuf.service.User;

class ProtobufCodeGenerationUnitTest {

	@Test
	void givenUserData_whenObjectCreated_thenDataShouldMatch() {
		final String firstName = "John";
		final String lastName = "Doe";
		final int age = 28;

		User user = User.newBuilder()
		.setFirstName(firstName)
		.setLastName(lastName)
		.setAge(age)
		.build();

		assertEquals(firstName, user.getFirstName());
		assertEquals(lastName, user.getLastName());
		assertEquals(age, user.getAge());
	}

}
