package com.baeldung.java.io.zip4j;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.EncryptionMethod;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ZipMultiFile {

    public static void main(String[] args) throws ZipException {
        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setEncryptFiles(true);
        zipParameters.setEncryptionMethod(EncryptionMethod.AES);

        List<File> filesToAdd = Arrays.asList(
                new File("aFile.txt"),
                new File("bFile.txt")
        );

        ZipFile zipFile = new ZipFile("compressed.zip", "password".toCharArray());
        zipFile.addFiles(filesToAdd, zipParameters);
    }
}
