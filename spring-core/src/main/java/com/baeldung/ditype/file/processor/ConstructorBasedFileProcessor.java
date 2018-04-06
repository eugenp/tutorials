package com.baeldung.ditype.file.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.ditype.file.reader.FileReader;
import com.baeldung.ditype.file.writer.FileWriter;

@Component
public class ConstructorBasedFileProcessor {

    private FileReader fileReader;
    private FileWriter fileWriter;
    
    @Autowired
    ConstructorBasedFileProcessor(FileReader fileReader,FileWriter fileWriter){
        this.fileReader = fileReader;
        this.fileWriter = fileWriter;
    }
    
    public void process(){
        fileReader.readFile();
        fileWriter.writeFile();
    }
}    