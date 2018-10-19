package com.baeldung.solr.fulltext.search.model;

import org.apache.solr.client.solrj.beans.Field;

public class Item {

    @Field
    private String id;

    @Field
    private String description;

    @Field
    private String category;

    @Field
    private float price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

}
