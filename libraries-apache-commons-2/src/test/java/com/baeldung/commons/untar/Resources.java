package com.baeldung.commons.untar;

import java.io.InputStream;

public interface Resources {
    InputStream TAR_FILE = Resources.class.getResourceAsStream("/untar/test.tar");
    InputStream TAR_GZ_FILE = Resources.class.getResourceAsStream("/untar/test.tar.gz");
}
