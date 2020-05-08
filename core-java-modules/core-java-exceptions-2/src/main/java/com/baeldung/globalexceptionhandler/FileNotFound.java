package com.baeldung.globalexceptionhandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileNotFound {

    private static Logger LOGGER = LoggerFactory.getLogger(FileNotFound.class);
    
    public static void main(String[] args) {
        
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File("/invalid/file/location")));
        } catch (FileNotFoundException e) {
            LOGGER.error("FileNotFoundException caught!");
        }
    }
    
}
