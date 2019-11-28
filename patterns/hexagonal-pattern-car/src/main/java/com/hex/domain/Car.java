package com.hex.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "car")
public class Car{
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "make", nullable = false)
    private String make;
 
    @Column(name = "color", nullable = false)
    private long color;
    
    public Car(){

    }
    public Car(Integer id, String name, String role, long salary) {
        this.id =   id;
        this.model = model;
        this.make = make;
        this.color = color;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public long getColor() {
		return color;
	}
	public void setColor(long color) {
		this.color = color;
	}

    

}