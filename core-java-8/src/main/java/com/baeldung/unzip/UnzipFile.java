package com.baeldung.unzip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipFile {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String fileZip = "/opt/zipped/cities.zip";
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
        ZipEntry zipEntry = zis.getNextEntry();
        while(zipEntry != null){
            String fileName = zipEntry.getName();
            File newFile = new File("/opt/unzipped/" + fileName);
            FileOutputStream fos = new FileOutputStream(newFile);             
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();   
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }
}