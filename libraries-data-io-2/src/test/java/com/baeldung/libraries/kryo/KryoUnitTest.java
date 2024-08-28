package com.baeldung.libraries.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

public class KryoUnitTest {

    private Kryo kryo;
    private Output output;
    private Input input;

    @Before
    public void init() {
        kryo = new Kryo();
        try {
            output = new Output(new FileOutputStream("src/test/resources/file.dat"));
            input = new Input(new FileInputStream("src/test/resources/file.dat"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KryoUnitTest.class.getName())
                .log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void givenObject_whenSerializing_thenReadCorrectly() {
        Object someObject = "Some string";

        kryo.writeClassAndObject(output, someObject);
        output.close();

        Object theObject = kryo.readClassAndObject(input);
        input.close();

        assertEquals(theObject, "Some string");
    }

    @Test
    public void givenObjects_whenSerializing_thenReadCorrectly() {
        String someString = "Multiple Objects";
        Date someDate = new Date(915170400000L);

        kryo.writeObject(output, someString);
        kryo.writeObject(output, someDate);
        output.close();

        String readString = kryo.readObject(input, String.class);
        Date readDate = kryo.readObject(input, Date.class);
        input.close();

        assertEquals(readString, "Multiple Objects");
        assertEquals(readDate.getTime(), 915170400000L);
    }

    @Test
    public void givenPerson_whenSerializing_thenReadCorrectly() {
        Person person = new Person();

        kryo.writeObject(output, person);
        output.close();

        Person readPerson = kryo.readObject(input, Person.class);
        input.close();

        assertEquals(readPerson.getName(), "John Doe");
    }

    @Test
    public void givenPerson_whenUsingCustomSerializer_thenReadCorrectly() {
        Person person = new Person();
        person.setAge(0);
        kryo.register(Person.class, new PersonSerializer());

        kryo.writeObject(output, person);
        output.close();

        Person readPerson = kryo.readObject(input, Person.class);
        input.close();

        assertEquals(readPerson.getName(), "John Doe");
        assertEquals(readPerson.getAge(), 18);
    }
    
    @Test
    public void givenPerson_whenCustomSerialization_thenReadCorrectly() {
        Person person = new Person();

        kryo.writeObject(output, person);
        output.close();

        Person readPerson = kryo.readObject(input, Person.class);
        input.close();

        assertEquals(readPerson.getName(), "John Doe");
        assertEquals(readPerson.getAge(), 18);
    }

    @Test
    public void givenJavaSerializable_whenSerializing_thenReadCorrectly() {
        ComplexClass complexClass = new ComplexClass();
        kryo.register(ComplexClass.class, new JavaSerializer());

        kryo.writeObject(output, complexClass);
        output.close();

        ComplexClass readComplexObject = kryo.readObject(input, ComplexClass.class);
        input.close();

        assertEquals(readComplexObject.getName(), "Bael");
    }

}
