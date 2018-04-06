package com.baeldung.ditype.file.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.ditype.file.reader.FileReader;
import com.baeldung.ditype.file.writer.FileWriter;

@Component
public class FieldBasedFileProcessor {

    @Autowired
    private FileReader fileReader;
    
    @Autowired
    private FileWriter fileWriter; 
   
    public void process(){
        fileReader.readFile();
        fileWriter.writeFile();
    }
    
}
