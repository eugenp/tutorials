package com.baeldung.serialization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

import com.baeldung.util.MySerializationUtils;

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

    @Test
    public void whenSerializingAndDeserializing_ThenObjectIsTheSame() throws IOException, ClassNotFoundException {
        Person p = new Person();
        p.setAge(20);
        p.setName("Joe");

        FileOutputStream fileOutputStream = new FileOutputStream("yofile.txt");
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(p);
        }

        FileInputStream fileInputStream = new FileInputStream("yofile.txt");
        try (ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            Person p2 = (Person) objectInputStream.readObject();
            assertEquals(p2.getAge(), p.getAge());
            assertEquals(p2.getName(), p.getName());
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
        assertEquals(p2.getAge(), p.getAge());
        assertEquals(p2.getName(), p.getName());
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
        assertEquals(p2.getAge(), p.getAge());
        assertEquals(p2.getName(), p.getName());
    }

    @Test(expected = ClassCastException.class)
    public void whenSerializingUsingCustomSerializationUtils_ThenThrowsError() throws IOException {
        Address address = new Address();
        address.setHouseNumber(10);
        MySerializationUtils.serialize((Serializable) address);
    }

    @Test
    public void whenSerializingAndDeserializingUsingCustomSerializationUtils_ThenObjectIsTheSame() throws IOException, ClassNotFoundException {
        Person p = new Person();
        p.setAge(20);
        p.setName("Joe");
        byte[] serialize = MySerializationUtils.serialize(p);
        Person p2 = MySerializationUtils.deserialize(serialize, Person.class);
        assertEquals(p2.getAge(), p.getAge());
        assertEquals(p2.getName(), p.getName());
    }

    @Test
    public void whenSerializingUsingCustomSerializationUtils_ThanOk() {
        assertFalse(MySerializationUtils.isSerializable(Address.class));
        assertTrue(MySerializationUtils.isSerializable(Person.class));
        assertTrue(MySerializationUtils.isSerializable(Integer.class));
    }
}
