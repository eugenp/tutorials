package com.baeuldung.serialization;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;

public class PersonTest {

	@Test
	public void testPeron() throws IOException, ClassNotFoundException   {
		Person p = new Person();
		p.setAge(20);
		p.setName("Joe");

		FileOutputStream fileOutputStream = new FileOutputStream("yofile.txt");
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(p);
		objectOutputStream.flush();
		objectOutputStream.close();

		FileInputStream fileInputStream = new FileInputStream("yofile.txt");
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		Person p2 = (Person) objectInputStream.readObject();
		objectInputStream.close();
		assertTrue(p2.getAge() == p.getAge());
		assertTrue(p2.getName().equals(p.getName()));
	}

}
