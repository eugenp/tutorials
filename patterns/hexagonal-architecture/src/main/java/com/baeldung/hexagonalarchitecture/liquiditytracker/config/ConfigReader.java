package com.baeldung.hexagonalarchitecture.liquiditytracker.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author VictorGil
 *
 * since March 2019
 */
public class ConfigReader{
	private static final Logger log = LoggerFactory.getLogger(ConfigReader.class);
	
    //this file must be present in the classpath
    private static final String CONFIG_FILE = "/config.json";
    
    public ConfigValues read() throws Exception{
        byte[] jsonBytes = null;
        log.debug("Going to read the configuration values from " + CONFIG_FILE);
        
        try{
           jsonBytes = readBytesFromClasspath(CONFIG_FILE); 
        } catch(URISyntaxException | IOException ex){
            String errorMessage = "Error when trying to read " + CONFIG_FILE + " file";
            log.error(errorMessage, ex);
            throw ex;
        }
        
        ObjectMapper objectMapper = new ObjectMapper();
        ConfigValues config = null;
        try{ 
            config = objectMapper.readValue(jsonBytes, ConfigValues.class);
        } catch(IOException ex){
            String errorMessage = "Error when trying to parse " + CONFIG_FILE + " file";
            log.error(errorMessage, ex);
            throw ex;
        }
        
        log.info("Application configuration: " + config);
        return config;
    }
    
    private byte[] readBytesFromClasspath(String filename) throws URISyntaxException, IOException{
        URL url = this.getClass().getResource(filename);
        URI uri = url.toURI();
        Path path = Paths.get(uri);
        log.debug("Path to the file: " + path);
        return Files.readAllBytes(path);
    }
}
