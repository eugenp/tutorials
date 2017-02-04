package com.baeldung.javaSerialization;

import org.junit.Assert;
import org.junit.Test;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class FileUtility {
    public static void serializeGivenObject(String filePath, Object object) throws Exception {
        FileOutputStream fileOut = new FileOutputStream(filePath);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(object);
        out.close();
        fileOut.close();
    }

    public static Object deserializeTheObjectBack(String filePath) throws Exception {
        FileInputStream fileIn = new FileInputStream(filePath);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Object deserializedObject = in.readObject();
        in.close();
        fileIn.close();
        return deserializedObject;
    }

}
