package org.baeldung.hexagonal.architecture;

/**
 * Core of the application - Business domain
 */

public class ProductDto {
	private Long id;
	private String type;
	private String description;

	// Getters and setters

	public String getType() {
		return type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

}
