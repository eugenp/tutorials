package org.baeldung.examples.olingo4;

import org.apache.olingo.server.api.OData;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

/**
 * Default implementation for ODataFactory 
 * @author Philippe
 *
 */
@Component
public class DefaultODataFactory implements ODataFactory {

    @Override
    public OData newInstance() {
        return OData.newInstance();
    }

}
