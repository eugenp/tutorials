package com.baeldung;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FilePersistence {

    private FileOutputStream outputStream;

    public FilePersistence(String location) throws FileNotFoundException {
        outputStream = new FileOutputStream(location);
    }

    public void addToBlacklist(Traveler traveler) throws IOException {
        //write to file
        byte[] nameBytes = traveler.getName().getBytes();
        outputStream.write(nameBytes);
        outputStream.close();
    }
}
