package com.baeldung.hexa.movie.domain;

public class MovieDTO {

	private String title;
	private int productionYear;
	
	public MovieDTO() {}
	
	public MovieDTO(String title, int productionYear) {
		this.title = title;
		this.productionYear = productionYear;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getProductionYear() {
		return productionYear;
	}
	public void setProductionYear(int productionYear) {
		this.productionYear = productionYear;
	}
}
