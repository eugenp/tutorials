package com.baeldung.java.io.zip4j;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.EncryptionMethod;

import java.io.File;
import java.io.IOException;

public class ZipSingleFile {

    public static void main(String[] args) throws IOException {
        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setEncryptFiles(true);
        zipParameters.setCompressionLevel(CompressionLevel.HIGHER);
        zipParameters.setEncryptionMethod(EncryptionMethod.AES);
        ZipFile zipFile = new ZipFile("compressed.zip", "password".toCharArray());

        File fileToAdd = new File("aFile.txt");
        if (!fileToAdd.exists()) {
            fileToAdd.createNewFile();
        }
        zipFile.addFile(fileToAdd, zipParameters);
        zipFile.close();
    }
}
