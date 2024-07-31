package com.baeldung.optionalreturntype;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Optional;

public class SerializeOptionalTypeExample {
    public static void main(String[] args) {
        User user1 = new User();
        user1.setUserId(1l);
        user1.setFirstName("baeldung");

        serializeObject(user1, "user1.ser");

        UserOptionalField user2 = new UserOptionalField();
        user2.setUserId(1l);
        user2.setFirstName(Optional.of("baeldung"));

        serializeObject(user2, "user2.ser");

    }

    public static void serializeObject(Object object, String fileName) {
        // Serialization
        try {
            FileOutputStream file = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(object);

            out.close();
            file.close();

            System.out.println("Object " + object.toString() + " has been serialized to file " + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
