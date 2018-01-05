package com.baeldung.beaninjectiontypes;

import java.io.File;

public class PdfFileChecker implements FileChecker {

    private static final int MAX_FILE_SIZE = 1024; //1 KB

    @Override
    public void checkFile(File file) {
        if (MAX_FILE_SIZE > file.length()) {
            throw new RuntimeException("Max file size is 1 KB !!");
        }
    }
}
