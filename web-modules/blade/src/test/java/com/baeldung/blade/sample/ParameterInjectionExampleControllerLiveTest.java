package com.baeldung.blade.sample;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class ParameterInjectionExampleControllerLiveTest {

    @Test
    public void givenFormParam_whenSet_thenRetrieveWithGet() throws Exception {
        URIBuilder builder = new URIBuilder("http://localhost:9000/params/form");
        builder.setParameter("name", "Andrea Ligios");

        final HttpUriRequest request = new HttpGet(builder.build());

        try (final CloseableHttpResponse httpResponse = HttpClientBuilder.create()
            .build()
            .execute(request);) {
            assertThat(EntityUtils.toString(httpResponse.getEntity())).isEqualTo("Andrea Ligios");
        }
    }

    @Test
    public void givenPathParam_whenSet_thenRetrieveWithGet() throws Exception {
        final HttpUriRequest request = new HttpGet("http://localhost:9000/params/path/1337");

        try (final CloseableHttpResponse httpResponse = HttpClientBuilder.create()
            .build()
            .execute(request);) {
            assertThat(EntityUtils.toString(httpResponse.getEntity())).isEqualTo("1337");
        }
    }

    // @Test
    // public void givenFileParam_whenSet_thenRetrieveWithGet() throws Exception {
    //
    // byte[] data = "this is some temp file content".getBytes("UTF-8");
    // java.nio.file.Path tempFile = Files.createTempFile("baeldung_test_tempfiles", ".tmp");
    // Files.write(tempFile, data, StandardOpenOption.WRITE);
    //
    // //HttpEntity entity = MultipartEntityBuilder.create().addPart("file", new FileBody(tempFile.toFile())).build();
    // HttpEntity entity = MultipartEntityBuilder.create().addTextBody("field1", "value1")
    // .addBinaryBody("fileItem", tempFile.toFile(), ContentType.create("application/octet-stream"), "file1.txt").build();
    //
    // final HttpPost post = new HttpPost("http://localhost:9000/params-file");
    // post.setEntity(entity);
    //
    // try (final CloseableHttpResponse httpResponse = HttpClientBuilder.create().build().execute(post);) {
    // assertThat(EntityUtils.toString(httpResponse.getEntity())).isEqualTo("file1.txt");
    // }
    // }

    @Test
    public void givenHeader_whenSet_thenRetrieveWithGet() throws Exception {
        final HttpUriRequest request = new HttpGet("http://localhost:9000/params/header");
        request.addHeader("customheader", "foobar");
        try (final CloseableHttpResponse httpResponse = HttpClientBuilder.create()
            .build()
            .execute(request);) {
            assertThat(EntityUtils.toString(httpResponse.getEntity())).isEqualTo("foobar");
        }
    }

    @Test
    public void givenNoCookie_whenCalled_thenReadDefaultValue() throws Exception {
        
        final HttpUriRequest request = new HttpGet("http://localhost:9000/params/cookie");
        try (final CloseableHttpResponse httpResponse = HttpClientBuilder.create()
            .build()
            .execute(request);) {
            assertThat(EntityUtils.toString(httpResponse.getEntity())).isEqualTo("default value");
        }

    }

}
