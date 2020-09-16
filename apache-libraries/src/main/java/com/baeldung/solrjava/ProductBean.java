package com.baeldung.solrjava;

import org.apache.solr.client.solrj.beans.Field;

public class ProductBean {

    String id;
    String name;
    String price;

    public ProductBean(String id, String name, String price) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    @Field("id")
    protected void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Field("name")
    protected void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    @Field("price")
    protected void setPrice(String price) {
        this.price = price;
    }
}
