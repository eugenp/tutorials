package com.baeldung.serializable_singleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SingletonSandbox {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Singleton s1 = Singleton.getInstance();
        s1.setState("State One");

        FileOutputStream fos = new FileOutputStream("singleton.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        FileInputStream fis = new FileInputStream("singleton.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);

        oos.writeObject(s1);

        Singleton s2 = (Singleton) ois.readObject();

        // State is preserved.
        System.out.println(s2.getState()); // Prints "State One".

        s2.setState("State Two");

        // Checking if they're the same instance.
        System.out.println(s1.equals(s2)); // Prints "False".

        oos.close();
        ois.close();
    }
}
