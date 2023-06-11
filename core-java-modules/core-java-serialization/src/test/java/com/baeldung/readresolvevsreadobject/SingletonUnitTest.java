package com.baeldung.readresolvevsreadobject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SingletonUnitTest {

    @Test
    public void testSingletonObj_withNoReadResolve() throws ClassNotFoundException, IOException {
        // Serialization
        FileOutputStream fos = new FileOutputStream("singleton.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        Singleton actualSingletonObject = Singleton.getInstance();
        oos.writeObject(actualSingletonObject);

        // Deserialization
        Singleton deserializedSingletonObject = null;
        FileInputStream fis = new FileInputStream("singleton.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        deserializedSingletonObject = (Singleton) ois.readObject();
        // remove readResolve() from Singleton class and uncomment this to test.
        //assertNotEquals(actualSingletonObject.hashCode(), deserializedSingletonObject.hashCode());
    }

    @Test
    public void testSingletonObj_withCustomReadResolve()
            throws ClassNotFoundException, IOException {
        // Serialization
        FileOutputStream fos = new FileOutputStream("singleton.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        Singleton actualSingletonObject = Singleton.getInstance();
        oos.writeObject(actualSingletonObject);

        // Deserialization
        Singleton deserializedSingletonObject = null;
        FileInputStream fis = new FileInputStream("singleton.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        deserializedSingletonObject = (Singleton) ois.readObject();
        assertEquals(actualSingletonObject.hashCode(), deserializedSingletonObject.hashCode());
    }
}
