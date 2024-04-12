package com.baeldung.gzipbytearray;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZip {

    private static final int BUFFER_SIZE = 512;

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
}
