package com.baeldung.deserialization;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerializaitonUtility {

    public static void main(String[] args) {
        AppleProduct macBook = new AppleProduct();
        macBook.headphonePort = "headphonePort2020";
        macBook.thunderboltPort = "thunderboltPort2020";

        try {
            FileOutputStream output = new FileOutputStream("./specs.txt");
            ObjectOutputStream out = new ObjectOutputStream(output);
            out.writeObject(macBook);
            out.close();
            output.close();
            System.out.println("Serialized data is saved to ./specs.txt");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}