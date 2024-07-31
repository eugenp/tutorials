package com.baeldung.boot.atlassearch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IndexConfig {

    @Value("${com.baeldung.atlas-search.index.query}")
    private String queryIndex;

    @Value("${com.baeldung.atlas-search.index.facet}")
    private String facetIndex;

    public String getFacetIndex() {
        return facetIndex;
    }

    public String getQueryIndex() {
        return queryIndex;
    }
}
