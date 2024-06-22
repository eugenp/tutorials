package com.baeldung.spring.data.es.aggregation.model;

import static org.springframework.data.elasticsearch.annotations.FieldType.Keyword;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "store-items")
public class StoreItem {
    @Id
    private String id;

    @Field(type = Keyword)
    private String type;
    @Field(type = Keyword)
    private String name;

    @Field(type = FieldType.Integer)
    private Long price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public StoreItem(String type, String name, Long price) {
        this.type = type;
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StoreItem storeItem = (StoreItem) o;
        return Objects.equals(type, storeItem.type) && Objects.equals(name, storeItem.name) && Objects.equals(price, storeItem.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name, price);
    }
}
