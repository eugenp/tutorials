package com.baeldung.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product{
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_description", nullable = false)
    private String desc;
 
    @Column(name = "price", nullable = false)
    private long price;
    
    public Product(){

    }
    public Product(Integer id, String productName, String desc, long price) {
        this.id =   id;
        this.productName = productName;
        this.desc = desc;
        this.price = price;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}

   

}