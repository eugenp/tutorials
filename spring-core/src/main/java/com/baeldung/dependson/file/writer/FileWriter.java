package com.baeldung.dependson.file.writer;

import com.baeldung.dependson.shared.File;


public class FileWriter {
    
    public FileWriter(File file){
        file.setText("write");
    }
    
    public void writeFile(){}
}
