package com.baeldung.dependson.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.baeldung.dependson.file.processor.FileProcessor;
import com.baeldung.dependson.file.reader.FileReader;
import com.baeldung.dependson.file.writer.FileWriter;

@Configuration
@ComponentScan("com.baeldung.dependson")
public class Config {

    @Bean
    @DependsOn({"fileReader","fileWriter"})
    public FileProcessor fileProcessor(){
        return new FileProcessor();
    }
    
    @Bean("fileReader")
    public FileReader fileReader(){
        return new FileReader();
    }
    
    @Bean("fileWriter")
    public FileWriter fileWriter(){
        return new FileWriter();
    }
   
}
