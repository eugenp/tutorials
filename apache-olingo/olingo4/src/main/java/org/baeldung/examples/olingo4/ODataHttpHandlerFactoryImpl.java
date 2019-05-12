package org.baeldung.examples.olingo4;

import java.util.Collections;
import java.util.List;

import org.apache.olingo.commons.api.edm.provider.CsdlEdmProvider;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataHttpHandler;
import org.apache.olingo.server.api.ServiceMetadata;
import org.apache.olingo.server.api.processor.Processor;

public class ODataHttpHandlerFactoryImpl implements ODataHttpHandlerFactory {

    private final ODataFactory odataFactory;
    private final CsdlEdmProvider edmProvider;
    private final List<Processor> processors;

    public ODataHttpHandlerFactoryImpl(ODataFactory odataFactory, CsdlEdmProvider edmProvider, List<Processor> processors) {
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

    public static class ODataHttpHandlerFactoryImplBuilder {

        private ODataFactory odataFactory;
        private CsdlEdmProvider edmProvider;
        private List<Processor> processors;

        public ODataHttpHandlerFactoryImplBuilder odataFactory(ODataFactory odataFactory) {
            this.odataFactory = odataFactory;
            return this;
        }

        public ODataHttpHandlerFactoryImplBuilder edmProvider(CsdlEdmProvider edmProvider) {
            this.edmProvider = edmProvider;
            return this;
        }

        public ODataHttpHandlerFactoryImplBuilder processors(List<Processor> processors) {
            this.processors = processors;
            return this;
        }

        public ODataHttpHandlerFactoryImpl build() {
            return new ODataHttpHandlerFactoryImpl(odataFactory, edmProvider, processors);
        }

    }

}
