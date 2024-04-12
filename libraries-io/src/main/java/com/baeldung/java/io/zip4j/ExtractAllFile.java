package com.baeldung.java.io.zip4j;

import java.io.IOException;

import net.lingala.zip4j.ZipFile;

public class ExtractAllFile {

    public static void main(String[] args) throws IOException {
        ZipFile zipFile = new ZipFile("compressed.zip", "password".toCharArray());
        zipFile.extractAll("/destination_directory");
        zipFile.close();
    }
}
