package com.baeldung.streamlargefile;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.streamlargefile.client.LargeFileDownloadWebClient;
import com.baeldung.streamlargefile.client.LimitedFileDownloadWebClient;
import com.baeldung.streamlargefile.server.LargeFileController;

class LargeFileControllerLiveTest {

    private static final String BASE_URL = "http://localhost:8081/large-file";
    private static final String DOWNLOAD_DESTINATION = LargeFileController.downloadPath.resolveSibling("download.dat")
        .toString();
    private static final Path downloadFile = LargeFileController.downloadPath;
    private static final Runtime runtime = Runtime.getRuntime();
    private static final Long xmx = runtime.maxMemory();

    private WebClient client = WebClient.create(BASE_URL);

    @BeforeAll
    static void init() throws IOException {
        if (!Files.exists(downloadFile)) {
            ClassPathResource res = new ClassPathResource("streamlargefile/generate-sample-files.sh");

            runtime.exec(res.getFile()
                .getAbsolutePath());
        }
    }

    @Test
    void givenMemorySafeClient_whenFileLargerThanXmx_thenFileDownloaded() throws IOException {
        if (xmx < Files.size(downloadFile)) {
            long size = LargeFileDownloadWebClient.fetch(client, DOWNLOAD_DESTINATION);
            assertTrue(size > xmx);
        }
    }

    @Test
    void givenLimitedClient_whenXmxLargerThanFile_thenFileDownloaded() throws IOException {
        WebClient client = WebClient.builder()
            .baseUrl(BASE_URL)
            .exchangeStrategies(LimitedFileDownloadWebClient.useMaxMemory())
            .build();

        if (xmx > Files.size(downloadFile)) {
            long size = LimitedFileDownloadWebClient.fetch(client, DOWNLOAD_DESTINATION);
            assertTrue(size < xmx);
        }
    }
}
