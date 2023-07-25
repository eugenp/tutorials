package com.baeldung.readresolvevsreadobject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class UserUnitTest {

    private static final String USER_SER = "user.ser";

    @After
    public void tearDown() {
        final File file = new File(USER_SER);
        if (file.exists()) {
            file.deleteOnExit();
        }
    }

    @Test
    public void testDeserializeObj_withOverriddenReadObject() throws ClassNotFoundException, IOException {
        // Serialization
        FileOutputStream fos = new FileOutputStream(USER_SER);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        User acutalObject = new User("Sachin", "Kumar");
        oos.writeObject(acutalObject);

        // Deserialization
        User deserializedUser;
        FileInputStream fis = new FileInputStream(USER_SER);
        ObjectInputStream ois = new ObjectInputStream(fis);
        deserializedUser = (User) ois.readObject();
        assertNotEquals(deserializedUser.hashCode(), acutalObject.hashCode());
        assertEquals(deserializedUser.getUserName(), "Sachin");
        assertEquals(deserializedUser.getPassword(), "Kumar");

        fos.close();
        oos.close();
        fis.close();
        ois.close();
    }

    @Test
    public void testDeserializeObj_withDefaultReadObject()
            throws ClassNotFoundException, IOException {
        // Serialization
        FileOutputStream fos = new FileOutputStream(USER_SER);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        User acutalObject = new User("Sachin", "Kumar");
        oos.writeObject(acutalObject);

        // Deserialization
        User deserializedUser;
        FileInputStream fis = new FileInputStream(USER_SER);
        ObjectInputStream ois = new ObjectInputStream(fis);
        deserializedUser = (User) ois.readObject();
        assertNotEquals(deserializedUser.hashCode(), acutalObject.hashCode());
        assertEquals(deserializedUser.getUserName(), "Sachin");
        // remove readObject() from User class and uncomment this to test.
        //assertEquals(deserializedUser.getPassword(), "xyzKumar");

        fos.close();
        oos.close();
        fis.close();
        ois.close();
    }
}
