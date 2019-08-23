package com.baeldung.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoggerUtil {

    public final static Logger LOG = Logger.getLogger("GLOBAL");
    
    static {
    	configuration();
    }
    
    private static void configuration() {
    	Properties props = new Properties();
    	try {
			props.load(
			  new BufferedReader(
			    new InputStreamReader(
			      LoggerUtil.class.getResourceAsStream("/log4jstructuraldp.properties")
			    )
			  )
			);
		} catch (IOException e) {
			System.out.println("log4jstructuraldp.properties file not configured properly");
			System.exit(0);
		}
    	PropertyConfigurator.configure(props);
    }
}
