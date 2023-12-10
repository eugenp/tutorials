package com.baeldung;

import java.io.*;

public class UtilCopy {

    /**
     * Creates a shallow copy by invoking the clone() method
     * @param person original object
     * @return shallow copy of the person
     */
    public static Person shallowCopy(Person person) {
        if (person != null) {
            try {
                return person.clone();
            } catch (Exception e) {
                System.out.println("Problem cloning Person: " + e.getMessage());
            }
        }
        return null;
    }

    public static Person deepCopy(Person person) {
        if (person != null) {
            try {
                // Serialize the original object into a byte array
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(person);

                // Deserialize the byte array to create a deep copy
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                return (Person) objectInputStream.readObject();
            } catch (Exception e) {
                System.out.println("Problem cloning Person: " + e.getMessage());
            }
        }
        return null;
    }
}
