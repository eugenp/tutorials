package com.baeldung.dependson.file.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.baeldung.dependson.file.reader.FileReader;
import com.baeldung.dependson.file.writer.FileWriter;

public class FileProcessor {

    @Autowired
    FileReader fileReader;
    
    @Autowired
    FileWriter fileWriter;
        
    public void process(){
        fileReader.readFile();
        fileWriter.writeFile();
    }
}