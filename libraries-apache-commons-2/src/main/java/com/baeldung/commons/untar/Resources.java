package com.baeldung.commons.untar;

import java.io.InputStream;

public interface Resources {

    static InputStream tarFile() {
        return Resources.class.getResourceAsStream("/untar/test.tar");
    }

    static InputStream tarGzFile() {
        return Resources.class.getResourceAsStream("/untar/test.tar.gz");
    }
}