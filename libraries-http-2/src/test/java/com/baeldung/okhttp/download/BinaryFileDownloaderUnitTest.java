package com.baeldung.okhttp.download;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BinaryFileDownloaderUnitTest {

    @Mock
    private OkHttpClient client;
    @Mock
    private BinaryFileWriter writer;
    @InjectMocks
    private BinaryFileDownloader tested;

    @Test
    public void givenUrlAndResponse_whenDownload_thenExpectFileWritten() throws Exception {
        String url = "http://example.com/file";
        Call call = mock(Call.class);
        when(client.newCall(any(Request.class))).thenReturn(call);
        ResponseBody body = ResponseBody.create("BODY", MediaType.get("application/text"));
        Response response = createResponse(url, body);
        when(call.execute()).thenReturn(response);
        when(writer.write(any(), anyDouble())).thenReturn(1L);

        try (BinaryFileDownloader tested = new BinaryFileDownloader(client, writer)) {
            long size = tested.download(url);
            assertEquals(1L, size);
            verify(writer).write(any(InputStream.class), anyDouble());
        }
        verify(writer).close();
    }

    @Test(expected = IllegalStateException.class)
    public void givenUrlAndResponseWithNullBody_whenDownload_thenExpectIllegalStateException() throws Exception {
        String url = "http://example.com/file";
        Call call = mock(Call.class);
        when(client.newCall(any(Request.class))).thenReturn(call);
        Response response = createResponse(url, null);
        when(call.execute()).thenReturn(response);

        tested.download(url);

        verify(writer, times(0)).write(any(InputStream.class), anyDouble());
    }

    @NotNull
    private Response createResponse(String url, ResponseBody body) {
        Request request = new Request.Builder().url(url).build();
        return new Response.Builder().code(200).request(request).protocol(Protocol.HTTP_2).message("Message").body(body).build();
    }

}