package com.baeldung.copyconstructor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DeepCopySerialization {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        SampleClass originalObject = new SampleClass(14, "Baeldung Article");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(originalObject);
        objectOutputStream.flush();

        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        SampleClass copyObject = (SampleClass) objectInputStream.readObject();

        // Verify that the copy is a deep copy
        System.out.println("Original Object: "+ originalObject);
        System.out.println("Copy Object: "+ copyObject);
        System.out.println("OriginalObject == CopyObject? " + (originalObject = copyObject));
        System.out.println("OriginalObject.equals(CopyObject)? " + (originalObject.equals(copyObject)));
    }

}

