package com.baeldung.download;

import org.apache.commons.io.FileUtils;
import org.asynchttpclient.*;

import java.io.*;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.ExecutionException;

public class FileDownload {

    static String FILE_URL = "http://ovh.net/files/10Mio.dat";
    static String FILE_NAME = "file.dat";

    private static void downloadWithJavaIO() {

        try (BufferedInputStream in = new BufferedInputStream(new URL(FILE_URL).openStream()); FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME)) {

            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void downloadWithJava7IO() {

        try (InputStream in = new URL(FILE_URL).openStream()) {
            Files.copy(in, Paths.get(FILE_NAME), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void downloadWithJavaNIO() throws IOException {

        URL url = new URL(FILE_URL);
        try (ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream()); 
            FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME); FileChannel fileChannel = fileOutputStream.getChannel()) {

            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        }
    }

    private static void downloadWithApacheCommons() {

        int CONNECT_TIMEOUT = 10000;
        int READ_TIMEOUT = 10000;
        try {
            FileUtils.copyURLToFile(new URL(FILE_URL), new File(FILE_NAME), CONNECT_TIMEOUT, READ_TIMEOUT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void downloadWithAHC() throws ExecutionException, InterruptedException, IOException {

        FileOutputStream stream = new FileOutputStream(FILE_NAME);
        AsyncHttpClient client = Dsl.asyncHttpClient();

        client.prepareGet(FILE_URL)
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

}
