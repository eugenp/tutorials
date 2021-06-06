package com.baeldung.okhttp.download;

import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class BinaryFileDownloaderIntegrationTest {

    @Rule
    public MockWebServer server = new MockWebServer();

    @Test
    public void givenATextFile_whenDownload_thenExpectFileDownloaded() {
        String body = "Hello Baeldung Readers!";
        server.enqueue(new MockResponse().setBody(body));
        String fileName = "download.txt";

        ProgressCallback progressCallback = progress -> assertEquals(100.0, progress, .0);
        try (BinaryFileWriter writer = new BinaryFileWriter(new FileOutputStream(fileName), progressCallback); BinaryFileDownloader tested = new BinaryFileDownloader(new OkHttpClient(), writer)) {
            long downloaded = tested.download(server.url("/greetings").toString());
            assertEquals(body.length(), downloaded);
            File downloadedFile = new File(fileName);
            assertTrue(downloadedFile.isFile());
            assertTrue(downloadedFile.delete());
        } catch (Exception e) {
            fail("An unexpected exception has occurred: " + e);
        }
    }

}
