package com.baeldung.java.io.zip4j;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.EncryptionMethod;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ZipMultiFile {

    public static void main(String[] args) throws IOException {
        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setEncryptFiles(true);
        zipParameters.setEncryptionMethod(EncryptionMethod.AES);

        File firstFile = new File("aFile.txt");
        File secondFile = new File("bFile.txt");
        if (!firstFile.exists()) {
            firstFile.createNewFile();
        }
        if (!secondFile.exists()) {
            secondFile.createNewFile();
        }

        List<File> filesToAdd = Arrays.asList(firstFile, secondFile);

        ZipFile zipFile = new ZipFile("compressed.zip", "password".toCharArray());
        zipFile.addFiles(filesToAdd, zipParameters);
        zipFile.close();
    }
}
