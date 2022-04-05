package com.baeldung.dependson.file.processor;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.dependson.file.reader.FileReader;
import com.baeldung.dependson.file.writer.FileWriter;
import com.baeldung.dependson.shared.File;

public class FileProcessor {

    File file;
      
    public FileProcessor(File file){
        this.file = file;
        if(file.getText().contains("write") && file.getText().contains("read")){
            file.setText("processed");
        }
    }  
}