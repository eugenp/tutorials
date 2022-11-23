package com.baeldung.serializable_singleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class EnumSingletonSandbox {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        EnumSingleton es1 = EnumSingleton.INSTANCE.getInstance();
        es1.setState("State One");

        FileOutputStream fos = new FileOutputStream("enum_singleton.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        FileInputStream fis = new FileInputStream("enum_singleton.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);

        oos.writeObject(es1);

        EnumSingleton es2 = (EnumSingleton) ois.readObject();

        // State is preserved.
        System.out.println(es2.getState()); // Prints "State One".

        // Checking if they're the same instance.
        System.out.println(es1.equals(es2)); // Prints "True".

        oos.close();
        ois.close();
    }
}