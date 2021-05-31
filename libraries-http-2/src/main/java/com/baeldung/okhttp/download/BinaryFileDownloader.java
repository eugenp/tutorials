package com.baeldung.okhttp.download;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class BinaryFileDownloader implements AutoCloseable {

  private final OkHttpClient client;
  private final BinaryFileWriter writer;

  public BinaryFileDownloader(OkHttpClient client, BinaryFileWriter writer) {
    this.client = client;
    this.writer = writer;
  }

  public long download(String url) throws IOException {
    Request request = createRequest(url);
    Response response = executeRequest(request);
    ResponseBody responseBody = getResponseBodyOrFail(response);
    return write(responseBody);
  }

  @NotNull
  private Request createRequest(String url) {
    return new Request.Builder().url(url).build();
  }

  @NotNull
  private Response executeRequest(Request request) throws IOException {
    return client.newCall(request).execute();
  }

  private ResponseBody getResponseBodyOrFail(Response response) {
    ResponseBody responseBody = response.body();
    if (responseBody == null) {
      throw new IllegalStateException("Response doesn't contain a file");
    }
    return responseBody;
  }

  private long write(ResponseBody responseBody) throws IOException {
    return writer.write(responseBody.byteStream());
  }

  @Override
  public void close() throws Exception {
    writer.close();
  }
}
