package com.baeldung.externalizable;

import java.io.*;

//http://programmertech.com/program/java/java-serialization-using-externalizable
//https://java2blog.com/externalizable-in-java/
//https://howtodoinjava.com/core-java/serialization/externalizable-vs-serializable/
//http://www.byteslounge.com/tutorials/java-externalizable-example
//http://www.java67.com/2012/10/difference-between-serializable-vs-externalizable-interface.html
public class ExternalizableTest {

    private final static String OUTPUT_FILE = "externalizable.txt";

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Country c = new Country();
        c.setCapital("Yerevan");
        c.setCode(374);
        c.setName("Armenia");

        FileOutputStream fileOutputStream = new FileOutputStream(OUTPUT_FILE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        c.writeExternal(objectOutputStream);

        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.close();

        FileInputStream fileInputStream = new FileInputStream(OUTPUT_FILE);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        Country c2 = new Country();
        c2.readExternal(objectInputStream);

        objectInputStream.close();
        fileInputStream.close();
    }

}
