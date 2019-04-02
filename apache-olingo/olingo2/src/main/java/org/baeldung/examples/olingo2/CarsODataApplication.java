package org.baeldung.examples.olingo2;


import java.util.Set;

import javax.ws.rs.ApplicationPath;

import org.apache.olingo.odata2.core.rest.ODataRootLocator;
import org.apache.olingo.odata2.core.rest.app.ODataApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CarsODataApplication extends ODataApplication {
    
    private static final Logger log = LoggerFactory.getLogger(CarsODataApplication.class);
    
    public CarsODataApplication() {
        super();
        log.info("[I17] Creating CarsODataApplication...");        
    }
    
}
