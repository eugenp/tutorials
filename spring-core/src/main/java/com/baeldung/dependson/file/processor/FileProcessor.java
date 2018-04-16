package com.baeldung.dependson.file.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.baeldung.dependson.file.reader.FileReader;
import com.baeldung.dependson.file.writer.FileWriter;
import com.baeldung.dependson.shared.File;

@Component
@DependsOn({"filereader", "fileWriter"})
public class FileProcessor {

    File file;
    @Autowired
    FileReader fileReader;
    
    @Autowired
    FileWriter fileWriter;
    
    public FileProcessor(File file){
        this.file = file;
        if(file.getText().contains("write") && file.getText().contains("read")){
            file.setText("processed");
        }
    }
    
    public String process(){
        fileReader.readFile();
        fileWriter.writeFile();
        return file.getText();
    }
}