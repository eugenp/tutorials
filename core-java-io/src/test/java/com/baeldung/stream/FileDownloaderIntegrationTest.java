package com.baeldung.stream;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileDownloaderIntegrationTest {

    private String fileUriPath;
    private String outputPath;
    private String saveAsFileName;
    private File outputFile;

    @Before
    public void setup() {
        fileUriPath = "http://speedtest.ftp.otenet.gr/files/test100k.db";
        outputPath = "/tmp/FileDownloaderUnitTest";
        saveAsFileName = "localFile.db";
        outputFile = new File(outputPath, saveAsFileName);
    }

    @After
    public void cleanUpLocalDir() {
        if (outputFile.exists()) {
            outputFile.delete();
            outputFile.getParentFile().delete();
        }
    }

    @Test
    public void givenHttpUrl_WhenFileDownloads_ThenCheckIfFileDownloadedSuccessfully() throws IOException, URISyntaxException {
        cleanUpLocalDir();
        FileDownloader.downloadFile(fileUriPath, outputPath, saveAsFileName);
        long existingFileSize = outputFile.length();
        Assert.assertTrue("File should be downloaded successfully.", existingFileSize > 0);
    }

    @Test
    public void givenHttpUrl_WhenFileDownloads_AndDeleteFilePortionFromEnd_ThenFileDownloadsWithResume() throws IOException, URISyntaxException {
        if (outputFile.exists()) {
            outputFile.delete();
        }
        givenHttpUrl_WhenFileDownloads_ThenCheckIfFileDownloadedSuccessfully();

        final long existingFileSize = outputFile.length();
        final int deltaByetsToBeDeleted = 10;

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(outputFile, "rw");) {
            randomAccessFile.setLength(existingFileSize - deltaByetsToBeDeleted);
        }
        Assert.assertTrue("File should be altered successfully.", existingFileSize - deltaByetsToBeDeleted == outputFile.length());
        long bytesDownloaded = FileDownloader.downloadFileWithResume(fileUriPath, outputPath, saveAsFileName);
        Assert.assertTrue("Bytes downloaded are not as expected.", bytesDownloaded == deltaByetsToBeDeleted);
        Assert.assertTrue("File should be resumed and downloaded successfully.", existingFileSize == outputFile.length());

        try {
            FileDownloader.downloadFileWithResume(fileUriPath, outputPath, saveAsFileName);
            Assert.fail("File was already downloaded, expected IOException.");
        } catch (IOException ex) {
            Assert.assertTrue(outputFile.length() == existingFileSize);
        }
    }

}
