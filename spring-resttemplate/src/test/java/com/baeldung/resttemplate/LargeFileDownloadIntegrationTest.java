package com.baeldung.resttemplate;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class LargeFileDownloadIntegrationTest {

    static String FILE_URL = "http://ovh.net/files/1Mio.dat";

    RestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void givenResumableUrl_whenUrlCalledByHeadOption_thenExpectHeadersAvailable() {
        HttpHeaders headers = restTemplate.headForHeaders(FILE_URL);
        Assertions
          .assertThat(headers.get("Accept-Ranges"))
          .contains("bytes");
        Assertions
          .assertThat(headers.getContentLength())
          .isGreaterThan(0);
    }

    @Test
    public void givenResumableUrl_whenDownloadCompletely_thenExpectCorrectFileSize() {
        HttpHeaders headers = restTemplate.headForHeaders(FILE_URL);
        long contentLength = headers.getContentLength();
        File file = restTemplate.execute(FILE_URL, HttpMethod.GET, null, clientHttpResponse -> {
            File ret = File.createTempFile("download", "tmp");
            StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
            return ret;
        });

        Assert.assertNotNull(file);
        Assertions
          .assertThat(file.length())
          .isEqualTo(contentLength);
    }

    @Test
    public void givenResumableUrl_whenDownloadRange_thenExpectFileSizeEqualOrLessThanRange() {
        int range = 10;
        File file = restTemplate.execute(FILE_URL, HttpMethod.GET, clientHttpRequest -> clientHttpRequest
          .getHeaders()
          .set("Range", String.format("bytes=0-%d", range - 1)), clientHttpResponse -> {
            File ret = File.createTempFile("download", "tmp");
            StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
            return ret;
        });

        Assert.assertNotNull(file);
        Assertions
          .assertThat(file.length())
          .isLessThanOrEqualTo(range);
    }

    @Test
    public void givenResumableUrl_whenPauseDownloadAndResume_thenExpectCorrectFileSize() {

        int range = 10;

        HttpHeaders headers = restTemplate.headForHeaders(FILE_URL);
        long contentLength = headers.getContentLength();

        File file = restTemplate.execute(FILE_URL, HttpMethod.GET, clientHttpRequest -> clientHttpRequest
          .getHeaders()
          .set("Range", String.format("bytes=0-%d", range - 1)), clientHttpResponse -> {
            File ret = File.createTempFile("download", "tmp");
            StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
            return ret;
        });

        Assert.assertNotNull(file);

        Assertions
          .assertThat(file.length())
          .isLessThanOrEqualTo(range);

        restTemplate.execute(FILE_URL, HttpMethod.GET, clientHttpRequest -> clientHttpRequest
          .getHeaders()
          .set("Range", String.format("bytes=%d-%d", file.length(), contentLength)), clientHttpResponse -> {
            StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(file, true));
            return file;
        });

        Assertions
          .assertThat(file.length())
          .isEqualTo(contentLength);

    }

}
