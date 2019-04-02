package org.baeldung.examples.olingo2;

import javax.persistence.EntityManager;

import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAServiceFactory;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * OData ServiceFactory that 
 * @author Philippe
 *
 */
@Component
public class CarsODataJPAServiceFactory extends ODataJPAServiceFactory {
    
    private EntityManager em;
    private static final Logger log = LoggerFactory.getLogger(CarsODataJPAServiceFactory.class);
    
    public CarsODataJPAServiceFactory(EntityManager em) {
        this.em = em;
    }
    
    @Override
    public ODataJPAContext initializeODataJPAContext() throws ODataJPARuntimeException {

        ODataJPAContext oDataJPAContext = getODataJPAContext();
        oDataJPAContext.setEntityManagerFactory(em.getEntityManagerFactory());
        oDataJPAContext.setPersistenceUnitName("default");
        
        return oDataJPAContext;
        
    }

}
