package com.baeldung.jackson.deserialization.dynamicobject;

import com.fasterxml.jackson.databind.JsonNode;

public class ProductJsonNode {

    private String name;
    private String category;
    private JsonNode details;

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

    public JsonNode getDetails() {
        return details;
    }

    public void setDetails(JsonNode details) {
        this.details = details;
    }

}
