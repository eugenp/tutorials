package com.baeldung.dependson.file.reader;

import com.baeldung.dependson.shared.File;

public class FileReader {

    public FileReader(File file) {
        file.setText("read");
    }
    
    public void readFile() {}
}
