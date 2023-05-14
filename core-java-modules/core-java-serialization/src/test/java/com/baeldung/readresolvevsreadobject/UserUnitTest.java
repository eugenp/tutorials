package com.baeldung.readresolvevsreadobject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class UserUnitTest {

    @Test
    public void testDeserializeObj_withOverriddenReadObject()
            throws ClassNotFoundException, IOException {
        // Serialization
        FileOutputStream fos = new FileOutputStream("user.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        User acutalObject = new User("Sachin", "Kumar");
        oos.writeObject(acutalObject);

        // Deserialization
        User deserializedUser = null;
        FileInputStream fis = new FileInputStream("user.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        deserializedUser = (User) ois.readObject();
        assertNotEquals(deserializedUser.hashCode(), acutalObject.hashCode());
        assertEquals(deserializedUser.getUserName(), "Sachin");
        assertEquals(deserializedUser.getPassword(), "Kumar");
    }

    @Test
    public void testDeserializeObj_withDefaultReadObject()
            throws ClassNotFoundException, IOException {
        // Serialization
        FileOutputStream fos = new FileOutputStream("user.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        User acutalObject = new User("Sachin", "Kumar");
        oos.writeObject(acutalObject);

        // Deserialization
        User deserializedUser = null;
        FileInputStream fis = new FileInputStream("user.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        deserializedUser = (User) ois.readObject();
        assertNotEquals(deserializedUser.hashCode(), acutalObject.hashCode());
        assertEquals(deserializedUser.getUserName(), "Sachin");
        // remove readObject() from User class and uncomment this to test.
        //assertEquals(deserializedUser.getPassword(), "xyzKumar");
    }
}
