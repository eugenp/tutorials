package com.baeldung.hexagonal.domain;

import java.io.Serializable;

/**
 * Domain object for Product
 * 
 * @author bhargavakotharu
 *
 */
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;

	private String type;

	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Product(Integer id, String name, String type, String description) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", type=" + type + ", description=" + description + "]";
	}

}
