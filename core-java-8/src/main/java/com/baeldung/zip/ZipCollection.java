package com.baeldung.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipCollection {
    public static void main(final String[] args) throws FileNotFoundException, IOException {
        String commonPath = System.getenv("TEST_PATH");
        String folderPhotos = commonPath + "/photos";
        String outFolder = commonPath + "/zipped/";
        FileOutputStream fos = new FileOutputStream(outFolder + "cities.zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File folder = new File(folderPhotos);
        File [] lstPhotos = folder.listFiles();
        for (final File currPhoto : lstPhotos) {
            addToZipFile(currPhoto, zipOut);
        }
        zipOut.close();
        fos.close();
    }

    public static void addToZipFile(final File file, final ZipOutputStream zos) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(file.getName());
        zos.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }
        zos.closeEntry();
        fis.close();
    }
}