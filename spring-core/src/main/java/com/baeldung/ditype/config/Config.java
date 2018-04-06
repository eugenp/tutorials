package com.baeldung.ditype.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.ditype.file.reader.FileReader;
import com.baeldung.ditype.file.writer.FileWriter;

@Configuration
@ComponentScan("com.baeldung.ditype")
public class Config {

    @Bean
    public FileReader fileReader(){
        return new FileReader();
    }
    
    @Bean
    public FileWriter fileWriter(){
        return new FileWriter();
    }
   
}
