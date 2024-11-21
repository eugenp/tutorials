package com.baeldung.download;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.ExecutionException;

import org.apache.commons.io.FileUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.HttpResponseBodyPart;
import org.asynchttpclient.Response;

public class FileDownload {

    public static void downloadWithJavaIO(String url, String localFilename) {

        try (BufferedInputStream in = new BufferedInputStream(new URI(url).toURL().openStream()); FileOutputStream fileOutputStream = new FileOutputStream(localFilename)) {

            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException |URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void downloadWithJava7IO(String url, String localFilename) {

        try (InputStream in = new URI(url).toURL().openStream()) {
            Files.copy(in, Paths.get(localFilename), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void downloadWithJavaNIO(String fileURL, String localFilename) throws IOException, URISyntaxException {

        URL url = new URI(fileURL).toURL();
        try (ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream()); 
            FileOutputStream fileOutputStream = new FileOutputStream(localFilename); FileChannel fileChannel = fileOutputStream.getChannel()) {

            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            fileOutputStream.close();
        }
    }

    public static void downloadWithApacheCommons(String url, String localFilename) {

        int CONNECT_TIMEOUT = 10000;
        int READ_TIMEOUT = 10000;
        try {
            FileUtils.copyURLToFile(new URI(url).toURL(), new File(localFilename), CONNECT_TIMEOUT, READ_TIMEOUT);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }

    public static void downloadWithAHC(String url, String localFilename) throws ExecutionException, InterruptedException, IOException {

        FileOutputStream stream = new FileOutputStream(localFilename);
        AsyncHttpClient client = Dsl.asyncHttpClient();

        client.prepareGet(url)
            .execute(new AsyncCompletionHandler<FileOutputStream>() {

                @Override
                public State onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
                    stream.getChannel()
                        .write(bodyPart.getBodyByteBuffer());
                    return State.CONTINUE;
                }

                @Override
                public FileOutputStream onCompleted(Response response) throws Exception {
                    return stream;
                }
            })
            .get();

        stream.getChannel().close();
        client.close();
    }

    public static void downloadWithApacheHttpClient(String url, String localFileName) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            httpClient.execute(httpGet, classicHttpResponse -> {
                int code = classicHttpResponse.getCode();
                if (code == 200) {
                    HttpEntity entity = classicHttpResponse.getEntity();
                    if (entity != null) {
                        try (InputStream inputStream = entity.getContent();
                        FileOutputStream fileOutputStream = new FileOutputStream(localFileName)) {
                            byte[] dataBuffer = new byte[1024];
                            int bytesRead;
                            while((bytesRead = inputStream.read(dataBuffer)) != -1) {
                                fileOutputStream.write(dataBuffer, 0, bytesRead);
                            }
                        }
                    }
                    EntityUtils.consume(entity);
                }
                return classicHttpResponse;
            });
        }
    }

}
