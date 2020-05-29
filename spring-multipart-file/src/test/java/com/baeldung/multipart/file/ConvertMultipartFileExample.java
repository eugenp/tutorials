package com.baeldung.multipart.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class ConvertMultipartFileExample {

    /**
     * Example of converting a {@link MultipartFile} to a {@link File} using {@link MultipartFile#getBytes()}.
     * 
     * @throws IOException
     */
    @Test
    public void whenGetBytes_thenOK() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("sourceFile.tmp", "Hello World".getBytes());

        File file = new File("src/main/resources/targetFile.tmp");

        OutputStream os = new FileOutputStream(file);

        os.write(multipartFile.getBytes());

        os.close();
    }

    /**
     * Example of converting a {@link MultipartFile} to a {@link File} using {@link MultipartFile#getInputStream()}.
     * 
     * @throws IOException
     */
    @Test
    public void whenGetInputStream_thenOK() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("sourceFile.tmp", "Hello World".getBytes());

        InputStream initialStream = multipartFile.getInputStream();
        byte[] buffer = new byte[initialStream.available()];
        initialStream.read(buffer);

        File targetFile = new File("src/main/resources/targetFile.tmp");
        OutputStream outStream = new FileOutputStream(targetFile);
        outStream.write(buffer);

        outStream.close();
    }

    /**
     * Example of converting a {@link MultipartFile} to a {@link File} using {@link MultipartFile#transferTo(File)}.
     * 
     * @throws IOException
     */
    @Test
    public void whenTransferTo_thenOK() throws IllegalStateException, IOException {
        MultipartFile multipartFile = new MockMultipartFile("sourceFile.tmp", "Hello World".getBytes());

        File file = new File("src/main/resources/targetFile.tmp");

        multipartFile.transferTo(file);
    }
}
