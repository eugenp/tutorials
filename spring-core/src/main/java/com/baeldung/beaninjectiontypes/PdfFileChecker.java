package com.baeldung.beaninjectiontypes;

import java.io.File;

public class PdfFileChecker implements FileChecker {

    private static final int MAX_FILE_SIZE = 1024; //1 KB

    @Override
    public boolean checkFile(File file) {
        if (file.length() > MAX_FILE_SIZE) {
            throw new RuntimeException("Max file size is 1 KB !!");
        }
        return true;
    }
}
