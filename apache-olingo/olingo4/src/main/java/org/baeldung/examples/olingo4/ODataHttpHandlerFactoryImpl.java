package org.baeldung.examples.olingo4;

import java.util.Collections;
import java.util.List;

import org.apache.olingo.commons.api.edm.provider.CsdlEdmProvider;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataHttpHandler;
import org.apache.olingo.server.api.ServiceMetadata;
import org.apache.olingo.server.api.processor.Processor;

import lombok.Builder;

@Builder
public class ODataHttpHandlerFactoryImpl implements ODataHttpHandlerFactory {
    
    
    private final ODataFactory odataFactory;
    private final CsdlEdmProvider edmProvider;
    private final List<Processor> processors;

    public ODataHttpHandlerFactoryImpl(ODataFactory odataFactory,CsdlEdmProvider edmProvider, List<Processor> processors) {
        this.odataFactory = odataFactory;
        this.edmProvider = edmProvider;
        this.processors = processors;
    }

    @Override
    public ODataHttpHandler newInstance() {
        
        OData odata = odataFactory.newInstance();
        ServiceMetadata metadata = odata.createServiceMetadata(edmProvider, Collections.emptyList());
        ODataHttpHandler handler = odata.createHandler(metadata);
        
        // Register all available processors
        processors.forEach(p -> handler.register(p));
        
        
        return handler;
    }

}
