package com.baeldung.stream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.nio.file.Paths;

public class FileDownloader {

    public static long downloadFile(String downloadUrl, String outputDirPath, String saveAsFileName)
        throws IOException, URISyntaxException {
        File outputDir = createFolder(outputDirPath);
        File outputFile = new File(outputDir, saveAsFileName);

        URLConnection downloadFileConnection = new URI(downloadUrl).toURL()
            .openConnection();

        return transferDataAndGetBytesDownloaded(downloadFileConnection, outputFile);
    }

    private static File createFolder(String outputDirPath) {
        File outputDir = Paths.get(outputDirPath)
            .toFile();
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        return outputDir;
    }

    private static long transferDataAndGetBytesDownloaded(URLConnection downloadFileConnection, File outputFile)
        throws IOException, FileNotFoundException {
        long bytesDownloaded = 0;
        try (InputStream is = downloadFileConnection.getInputStream();
            OutputStream os = new FileOutputStream(outputFile, true)) {

            byte[] buffer = new byte[1024];

            int bytesCount;
            while ((bytesCount = is.read(buffer)) > 0) {
                os.write(buffer, 0, bytesCount);
                bytesDownloaded += bytesCount;
            }
        }
        return bytesDownloaded;
    }

    public static long downloadFileWithResume(String downloadUrl, String outputDirPath, String saveAsFileName)
        throws IOException, URISyntaxException {
        File outputDir = createFolder(outputDirPath);
        File outputFile = new File(outputDir, saveAsFileName);

        URLConnection downloadFileConnection = addFileResumeFunctionality(downloadUrl, outputFile);
        return transferDataAndGetBytesDownloaded(downloadFileConnection, outputFile);
    }

    private static URLConnection addFileResumeFunctionality(String downloadUrl, File outputFile) throws IOException, MalformedURLException, URISyntaxException, ProtocolException {
        long existingFileSize = 0L;
        URLConnection downloadFileConnection = new URI(downloadUrl).toURL()
            .openConnection();

        if (outputFile.exists() && downloadFileConnection instanceof HttpURLConnection) {
            HttpURLConnection httpFileConnection = (HttpURLConnection) downloadFileConnection;

            HttpURLConnection tmpFileConn = (HttpURLConnection) new URI(downloadUrl).toURL()
                .openConnection();
            tmpFileConn.setRequestMethod("HEAD");
            long fileLength = tmpFileConn.getContentLengthLong();
            existingFileSize = outputFile.length();

            if (existingFileSize < fileLength) {
                httpFileConnection.setRequestProperty("Range", "bytes=" + existingFileSize + "-");
            } else {
                throw new IOException("File Download already completed.");
            }
        }
        return downloadFileConnection;
    }
}
