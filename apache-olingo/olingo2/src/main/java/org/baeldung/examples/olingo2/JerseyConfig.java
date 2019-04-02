package org.baeldung.examples.olingo2;

import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;

import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.core.rest.ODataRootLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
@ApplicationPath("/odata")
public class JerseyConfig extends ResourceConfig {
    
    
    public JerseyConfig(CarsODataApplication delegate,ServletContext servletContext,CarsODataJPAServiceFactory serviceFactory) {        
        
        delegate
          .getClasses()
          .forEach( c -> {
              // Avoid using the default Locator
              if ( !ODataRootLocator.class.isAssignableFrom(c)) {
                  register(c);
              }
          });
        
        register(new CustomLocator(serviceFactory)); 
        
    }
    
    
    @Path("/")
    public static class CustomLocator extends ODataRootLocator {

        private CarsODataJPAServiceFactory serviceFactory;

        public CustomLocator(CarsODataJPAServiceFactory serviceFactory) {
            this.serviceFactory = serviceFactory;
        }

        @Override
        public ODataServiceFactory getServiceFactory() {
            return this.serviceFactory;
        }
        
    }

}
