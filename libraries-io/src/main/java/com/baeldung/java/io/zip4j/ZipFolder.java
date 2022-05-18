package com.baeldung.java.io.zip4j;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.EncryptionMethod;

import java.io.File;
import java.io.IOException;

public class ZipFolder {

    public static void main(String[] args) throws IOException {
        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setEncryptFiles(true);
        zipParameters.setEncryptionMethod(EncryptionMethod.AES);
        ZipFile zipFile = new ZipFile("compressed.zip", "password".toCharArray());
        zipFile.addFolder(new File("/users/folder_to_add"), zipParameters);
        zipFile.close();
    }
}
