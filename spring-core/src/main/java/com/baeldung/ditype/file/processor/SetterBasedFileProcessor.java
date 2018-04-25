package com.baeldung.ditype.file.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.ditype.file.reader.FileReader;
import com.baeldung.ditype.file.writer.FileWriter;

@Component
public class SetterBasedFileProcessor {

    private FileReader fileReader;
    private FileWriter fileWriter; 
   
    public void process(){
        fileReader.readFile();
        fileWriter.writeFile();
    }

    @Autowired
    public void setFileReader(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    @Autowired
    public void setFileWriter(FileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }
    
}
