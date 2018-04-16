package com.baeldung.dependson.processor;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.dependson.config.Config;
import com.baeldung.dependson.file.processor.FileProcessor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class FileProcessorTest {

    @Autowired
    ApplicationContext context;
    
    @Test
    public void WhenFileProcessorIsCreated_FileTextContains_Processed() {
        FileProcessor processor = context.getBean(FileProcessor.class);
        assertTrue(processor.process().contains("processed"));
    }

}
