package com.baeldung.logreg;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for digit classifier.
 * 
 */
public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    private Utils() {
    }

    /**
     * Download the content of the given url and save it into a file.
     * @param url
     * @param file
     */
    public static void downloadAndSave(String url, File file) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create()
            .build();
        logger.info("Connecting to {}", url);
        try (CloseableHttpResponse response = client.execute(new HttpGet(url))) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                logger.info("Downloaded {} bytes", entity.getContentLength());
                try (FileOutputStream outstream = new FileOutputStream(file)) {
                    logger.info("Saving to the local file");
                    entity.writeTo(outstream);
                    outstream.flush();
                    logger.info("Local file saved");
                }
            }
        }
    }

    /**
     * Extract a "tar.gz" file into a given folder.
     * @param file
     * @param folder 
     */
    public static void extractTarArchive(File file, String folder) throws IOException {
        logger.info("Extracting archive {} into folder {}", file.getName(), folder);
        // @formatter:off
        try (FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            GzipCompressorInputStream gzip = new GzipCompressorInputStream(bis); 
            TarArchiveInputStream tar = new TarArchiveInputStream(gzip)) {
       // @formatter:on
            TarArchiveEntry entry;
            while ((entry = (TarArchiveEntry) tar.getNextEntry()) != null) {
                extractEntry(entry, tar, folder);
            }
        }
      logger.info("Archive extracted"); 
    }

    /**
     * Extract an entry of the input stream into a given folder
     * @param entry
     * @param tar
     * @param folder
     * @throws IOException
     */
    public static void extractEntry(ArchiveEntry entry, InputStream tar, String folder) throws IOException {
        final int bufferSize = 4096;
        final String path = folder + entry.getName();
        if (entry.isDirectory()) {
            new File(path).mkdirs();
        } else {
            int count;
            byte[] data = new byte[bufferSize];
            // @formatter:off
            try (FileOutputStream os = new FileOutputStream(path); 
                BufferedOutputStream dest = new BufferedOutputStream(os, bufferSize)) {
           // @formatter:off
                while ((count = tar.read(data, 0, bufferSize)) != -1) {
                    dest.write(data, 0, count);
                }
            }
        }
    }
}
