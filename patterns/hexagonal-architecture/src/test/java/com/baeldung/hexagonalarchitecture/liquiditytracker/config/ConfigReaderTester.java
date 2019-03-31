package com.baeldung.hexagonalarchitecture.liquiditytracker.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public class ConfigReaderTester {
    private static final Logger log = LoggerFactory.getLogger(ConfigReaderTester.class);
    
    public static void main(String[] args) throws Exception {
        ConfigReader configReader = new ConfigReader();
        
        ConfigValues configValues = configReader.read();
        
        log.info("Configuration read: " + configValues);
    }
}
