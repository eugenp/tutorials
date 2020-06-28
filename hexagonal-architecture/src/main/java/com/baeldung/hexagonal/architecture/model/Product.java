/**
 * 
 */
package com.baeldung.hexagonal.architecture.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author AshwiniKeshri
 *
 */

@Entity
@Table(name = "PRODUCT")
public class Product  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4000353732860709995L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name = "QUANTITY")
	private Long quantity;
	
	@Column(name = "PRICE")
	private Double price;
	
	@Column(name = "DESCRIPTION")
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity > 0 ? quantity : 0;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price == null ? 0.0 : price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}  
	
	
	
}
