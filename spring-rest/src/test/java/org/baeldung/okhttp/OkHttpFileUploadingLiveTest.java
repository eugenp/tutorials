package org.baeldung.okhttp;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import org.baeldung.okhttp.ProgressRequestWrapper;
import org.junit.Test;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpFileUploadingLiveTest {

    private static final String BASE_URL = "http://localhost:8080/spring-rest";

    @Test
    public void whenUploadFile_thenCorrect() throws IOException {

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
          .setType(MultipartBody.FORM)
          .addFormDataPart("file", "file.txt",
            RequestBody.create(MediaType.parse("application/octet-stream"), new File("src/test/resources/test.txt")))
          .build();

        Request request = new Request.Builder()
                .url(BASE_URL + "/users/upload")
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();

        assertThat(response.code(), equalTo(200));
    }

    @Test
    public void whenGetUploadFileProgress_thenCorrect() throws IOException {

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
          .setType(MultipartBody.FORM)
          .addFormDataPart("file", "file.txt",
            RequestBody.create(MediaType.parse("application/octet-stream"), new File("src/test/resources/test.txt")))
          .build();


        ProgressRequestWrapper.ProgressListener listener = new ProgressRequestWrapper.ProgressListener() {

            public void onRequestProgress(long bytesWritten, long contentLength) {

                float percentage = 100f * bytesWritten / contentLength;
                assertFalse(Float.compare(percentage, 100) > 0);
            }
        };

        ProgressRequestWrapper countingBody = new ProgressRequestWrapper(requestBody, listener);

        Request request = new Request.Builder()
                .url(BASE_URL + "/users/upload")
                .post(countingBody)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();

        assertThat(response.code(), equalTo(200));

    }
}
