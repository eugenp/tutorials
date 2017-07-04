package com.baeldung.deserialization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class DeserializationUtility {

    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException {

        final String fileName = "./specs.txt";

        // deserialize object
        deSerializeObjectFromFile(fileName);

    }

    protected static void deSerializeObjectFromFile(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
        AppleProduct macbook = null;
        try (ObjectInputStream fileInputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            macbook = (AppleProduct) fileInputStream.readObject();
        }
        System.out.println("Deserializing AppleProduct...");
        System.out.println("Headphone port of AppleProduct: " + macbook.getHeadphonePort());
        System.out.println("Thunderbolt port of AppleProduct: " + macbook.getThunderboltPort());
    }

}