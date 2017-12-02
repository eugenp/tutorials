package com.baeldung.hibernatesearch.model;

import org.hibernate.search.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Indexed
@Table(name = "product")
public class Product {

    @Id
    private int id;

    @Field(termVector = TermVector.YES)
    private String productName;

    @Field(termVector = TermVector.YES)
    private String description;

    @Field
    private int memory;

    public Product(int id, String productName, int memory, String description) {
        this.id = id;
        this.productName = productName;
        this.memory = memory;
        this.description = description;
    }

    public Product() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Product))
            return false;

        Product product = (Product) o;

        if (id != product.id)
            return false;
        if (memory != product.memory)
            return false;
        if (!productName.equals(product.productName))
            return false;
        return description.equals(product.description);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + productName.hashCode();
        result = 31 * result + memory;
        result = 31 * result + description.hashCode();
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
