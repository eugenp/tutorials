package com.baeldung.gzip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZip {

    protected static final int BUFFER_SIZE = 512;

    public static void gzip(InputStream is, OutputStream os) throws IOException {
        GZIPOutputStream gzipOs = new GZIPOutputStream(os);
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = 0;
        while ((bytesRead = is.read(buffer)) > -1) {
            gzipOs.write(buffer, 0, bytesRead);
        }
        gzipOs.close();
    }


    public static void gunzip(InputStream is, OutputStream os) throws IOException {
        GZIPInputStream gzipIs = new GZIPInputStream(is);
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = 0;
        while ((bytesRead = gzipIs.read(buffer)) > -1) {
            os.write(buffer, 0, bytesRead);
        }
    }

    public static void main(String[] args) throws IOException {
        String payload = "This is a sample text to test methods gzip and gunzip. Have a nice day!";
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        gzip(new ByteArrayInputStream(payload.getBytes()), os);
        byte[] compressed = os.toByteArray();
        ByteArrayOutputStream ungzipOS = new ByteArrayOutputStream();
        gunzip(new ByteArrayInputStream(compressed), ungzipOS);
        System.out.println(String.format("decompressed text: %s", new String(ungzipOS.toByteArray())));
    }
}
