package com.baeldung.deserialization;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class DeserializationUtility {
    public static void main(String[] args) {
        AppleProduct macbook = null;
        try {
            FileInputStream inputFile = new FileInputStream("./specs.txt");
            ObjectInputStream fileInputStream = new ObjectInputStream(inputFile);
            macbook = (AppleProduct) fileInputStream.readObject();
            fileInputStream.close();
            inputFile.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        System.out.println("Deserializing AppleProduct...");
        System.out.println("Headphone port of AppleProduct: " + macbook.headphonePort);
        System.out.println("Thunderbolt port of AppleProduct: " + macbook.thunderboltPort);
    }
}