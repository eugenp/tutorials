package com.baeldung.objecthydration;

import java.io.*;

public class UserSerialisationDeserialisation {
    public void serialisedUser(User user, String outputName) {
        try {
            FileOutputStream fileOut = new FileOutputStream(outputName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(user);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User deserialiseUser(String serialisedFile) {
        User deserializedUser = null;
        try {
            FileInputStream fileIn = new FileInputStream(serialisedFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            deserializedUser = (User) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            File f = new File(serialisedFile);
            f.delete();
        }
        return deserializedUser;
    }
}
