package com.baeldung.abstractclasses.application;

import com.baeldung.abstractclasses.filereaders.BaseFileReader;
import com.baeldung.abstractclasses.filereaders.LowercaseFileReader;
import com.baeldung.abstractclasses.filereaders.StandardFileReader;
import com.baeldung.abstractclasses.filereaders.UppercaseFileReader;
import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        
        Application application = new Application();
        String filePath = application.getFilePathFromResourcesFolder("files/test.txt");
            
        BaseFileReader lowercaseFileReader = new LowercaseFileReader(filePath);
        lowercaseFileReader.readFile().forEach(line -> System.out.println(line));

        BaseFileReader upperCaseFileReader = new UppercaseFileReader(filePath);
        upperCaseFileReader.readFile().forEach(line -> System.out.println(line));

        BaseFileReader standardFileReader = new StandardFileReader(filePath);
        standardFileReader.readFile().forEach(line -> System.out.println(line));
         
    }
    
    private String getFilePathFromResourcesFolder(String fileName) {
        return getClass().getClassLoader().getResource(fileName).getPath().substring(1);
    }
}
