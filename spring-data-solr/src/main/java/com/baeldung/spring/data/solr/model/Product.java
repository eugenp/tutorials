package com.baeldung.spring.data.solr.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(solrCoreName = "product")
public class Product {
    
    @Id
    @Indexed(name = "id", type = "string")
    private String id;
    
    @Indexed(name = "name", type = "string")
    private String name;
    
    @Indexed(name = "category", type = "string")
    private String category;
    
    @Indexed(name = "description", type = "string")
    private String description;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
