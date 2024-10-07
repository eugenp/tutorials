package com.baeldung.getwebfilesize;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

class GetWebFileSizeUnitTest {

    String fileUrl = "https://www.ingka.com/wp-content/uploads/2020/11/dummy.pdf";

    private static final long EXPECTED_FILE_SIZE = 427;

    @Test
    void givenUrl_whenGetFileSizeUsingURLConnectionAndGetContentLengthLong_thenCorrect() throws IOException {
        URL url = new URL(fileUrl);
        URLConnection urlConnection = url.openConnection();

        long fileSize = urlConnection.getContentLengthLong();
        if (fileSize != -1) {
            assertEquals(EXPECTED_FILE_SIZE, fileSize);
        } else {
            fail("Could not determine file size");
        }

    }

    @Test
    void givenUrl_whenGetFileSizeUsingURLConnectionAndGetHeaderField_thenCorrect() throws IOException {
        URL url = new URL(fileUrl);
        URLConnection urlConnection = url.openConnection();

        String headerField = urlConnection.getHeaderField("Content-Length");
        if (headerField != null && !headerField.isEmpty()) {
            long fileSize = Long.parseLong(headerField);
            assertEquals(EXPECTED_FILE_SIZE, fileSize);

        } else {
            fail("Could not determine file size");
        }

    }

}
