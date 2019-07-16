package com.shopping.zone.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "USR_INF")
public class User {

	@Id
	@Column(name = "ID")
	private int id;

	@Column(name = "FULL_NAME")
	private String name;

	@Column(name = "REG_DATE")
	private LocalDate registeredDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(LocalDate registeredDate) {
		this.registeredDate = registeredDate;
	}

}
