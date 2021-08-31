package com.baeldung.okhttp.download;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.CONTENT_LENGTH;

public class BinaryFileDownloader implements AutoCloseable {

    private final OkHttpClient client;
    private final BinaryFileWriter writer;

    public BinaryFileDownloader(OkHttpClient client, BinaryFileWriter writer) {
        this.client = client;
        this.writer = writer;
    }

    public long download(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        ResponseBody responseBody = response.body();
        if (responseBody == null) {
            throw new IllegalStateException("Response doesn't contain a file");
        }
        double length = Double.parseDouble(Objects.requireNonNull(response.header(CONTENT_LENGTH, "1")));
        return writer.write(responseBody.byteStream(), length);
    }

    @Override
    public void close() throws Exception {
        writer.close();
    }
}
