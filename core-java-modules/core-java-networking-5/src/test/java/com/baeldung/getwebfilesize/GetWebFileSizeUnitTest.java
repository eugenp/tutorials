package com.baeldung.getwebfilesize;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

class GetWebFileSizeUnitTest {

    String fileUrl = "https://www.ingka.com/wp-content/uploads/2020/11/dummy.pdf";

    @Test
    void givenUrl_whenGetFileSizeUsingURLConnection_thenCorrect() throws IOException {
        URL url = new URL(fileUrl);
        URLConnection urlConnection = url.openConnection();

        long fileSize = urlConnection.getContentLengthLong();
        if (fileSize != -1) {
            assertEquals(29789, fileSize);
        } else {
            fail("Could not determine file size");
        }

    }
}
