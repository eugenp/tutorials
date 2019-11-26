package com.baeldung.jackson.nested;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = ProductDeserializer.class)
public class Product {

    private String id;
    private String name;
    private String brandName;
    private String ownerName;

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

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("brand")
    private void unpackNested(Map<String, Object> brand) {
        this.brandName = (String) brand.get("name");
        Map<String, String> owner = (Map<String, String>) brand.get("owner");
        this.ownerName = owner.get("name");
    }
}