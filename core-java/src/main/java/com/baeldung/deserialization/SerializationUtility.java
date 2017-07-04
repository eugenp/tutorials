package com.baeldung.deserialization;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerializationUtility {

    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException {

        AppleProduct macBook = new AppleProduct();
        macBook.headphonePort = "headphonePort2020";
        macBook.thunderboltPort = "thunderboltPort2020";

        // serialize object
        serializeObjectToFile(macBook);

    }

    public static void serializeObjectToFile(Object appleProduct) {

        String fileName = "./specs.txt";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(appleProduct);
            System.out.println("Serialized data is saved to " + fileName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}