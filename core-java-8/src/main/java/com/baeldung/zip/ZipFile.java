package com.baeldung.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFile {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String folderPhotos = "/opt/photos/";
        String outFolder = "/opt/zipped/";
        FileOutputStream fos = new FileOutputStream(outFolder + "cities.zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File folder = new File(folderPhotos);
        File [] lstFiles = folder.listFiles();
        if (lstFiles.length > 0) {
            File currFile = lstFiles[0];
            addToZipFile(currFile, zipOut);
        }
        zipOut.close();
        fos.close();
    }

    public static void addToZipFile(File file, ZipOutputStream zos) throws FileNotFoundException, IOException {
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