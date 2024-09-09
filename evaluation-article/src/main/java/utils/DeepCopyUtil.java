package utils;

import models.Person;

import java.io.*;

public class DeepCopyUtil {

    private DeepCopyUtil() {
    }

    public static <T extends Serializable> T deepCopy(T object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Person shallowCopy(Person person) {
        // Note: This is a shallow copy, so the address list is not deep-copied
        return new Person(person.getName(), person.getAge(), person.getPhoneNumbers());
    }
}
