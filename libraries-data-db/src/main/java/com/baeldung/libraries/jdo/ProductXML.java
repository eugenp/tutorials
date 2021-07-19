package com.baeldung.libraries.jdo;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import javax.xml.bind.annotation.XmlAttribute;

@PersistenceCapable()
public class ProductXML {

    @XmlAttribute
    private long productNumber = 0;
    @PrimaryKey
    private String name = null;
    private Double price = 0.0;

    public ProductXML() {
        this.productNumber = 0;
        this.name = null;
        this.price = 0.0;
    }

    public ProductXML(long productNumber, String name, Double price) {
        this.productNumber = productNumber;
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}