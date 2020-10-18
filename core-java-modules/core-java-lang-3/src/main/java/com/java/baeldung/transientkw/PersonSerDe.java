package com.java.baeldung.transientkw;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PersonSerDe {
    static String fileName = "person.ser";
    
    /**
     * Method to serialize Person objects to the file
     */
    public static void serialize(Person person) {
        try {
            FileOutputStream file = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(person);

            out.close();
            file.close();

        }

        catch (IOException ex) {
            System.out.println("IOException is caught");
        }
    }
    
    /**
     * Method to deserialize the person object
     * @return person
     * @throws IOException, ClassNotFoundException
     */
    public static Person deserialize() {
        try {
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(file);

            Person person = (Person) in.readObject();

            in.close();
            file.close();

            return person;
        }

        catch (IOException | ClassNotFoundException ex) {
            System.out.println("IOException is caught");
        }
        return null;
    }
}
