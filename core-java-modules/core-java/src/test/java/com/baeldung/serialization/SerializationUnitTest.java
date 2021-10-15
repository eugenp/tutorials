package com.baeldung.serialization;

import static org.junit.Assert.assertTrue;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

public class SerializationUnitTest {

	@Test(expected = NotSerializableException.class)
	public void whenSerializing_ThenThrowsError() throws IOException {
		Address address = new Address();
		address.setHouseNumber(10);
		FileOutputStream fileOutputStream = new FileOutputStream("yofile.txt");
		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
			objectOutputStream.writeObject(address);
		}
	}

	@Test(expected = ClassCastException.class)
	public void whenSerializingUsingApacheCommons_ThenThrowsError() {
		Address address = new Address();
		address.setHouseNumber(10);
		SerializationUtils.serialize((Serializable) address);
	}

	@Test
	public void whenSerializingAndDeserializingUsingApacheCommons_ThenObjectIsTheSame() {
		Person p = new Person();
		p.setAge(20);
		p.setName("Joe");
		byte[] serialize = SerializationUtils.serialize(p);
		Person p2 = (Person) SerializationUtils.deserialize(serialize);
		assertTrue(p2.getAge() == p.getAge());
		assertTrue(p2.getName().equals(p.getName()));
	}

	@Test(expected = ClassCastException.class)
	public void whenSerializingUsingSpringSerializationUtils_ThenThrowsError() {
		Address address = new Address();
		address.setHouseNumber(10);
		org.springframework.util.SerializationUtils.serialize((Serializable) address);
	}

	@Test
	public void whenSerializingAndDeserializingUsingSpringSerializationUtils_ThenObjectIsTheSame() {
		Person p = new Person();
		p.setAge(20);
		p.setName("Joe");
		byte[] serialize = org.springframework.util.SerializationUtils.serialize(p);
		Person p2 = (Person) org.springframework.util.SerializationUtils.deserialize(serialize);
		assertTrue(p2.getAge() == p.getAge());
		assertTrue(p2.getName().equals(p.getName()));
	}

	@Test(expected = ClassCastException.class)
	public void whenSerializingUsingCustomSerializationUtils_ThenThrowsError() throws IOException {
		Address address = new Address();
		address.setHouseNumber(10);
		com.baeldung.util.SerializationUtils.serialize((Serializable) address);
	}

	@Test
	public void whenSerializingAndDeserializingUsingCustomSerializationUtils_ThenObjectIsTheSame() throws IOException, ClassNotFoundException {
		Person p = new Person();
		p.setAge(20);
		p.setName("Joe");
		byte[] serialize = com.baeldung.util.SerializationUtils.serialize(p);
		Person p2 = com.baeldung.util.SerializationUtils.deserialize(serialize, Person.class);
		assertTrue(p2.getAge() == p.getAge());
		assertTrue(p2.getName().equals(p.getName()));
	}
}
