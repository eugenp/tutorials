package com.baeldung.java.io.zip4j;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class ExtractSingleFile {

    public static void main(String[] args) throws ZipException {
        ZipFile zipFile = new ZipFile("compressed.zip", "password".toCharArray());
        zipFile.extractFile("aFile.txt", "/destination_directory");
    }
}
