package com.baeldung.hibernate.lob.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity 
@Table(name="users")
public class User {

    @Id
	private String id; 
	 
	@Column(name = "name", columnDefinition="VARCHAR(128)") 
	private String name; 
	 
	@Lob 
	@Column(name = "photo", columnDefinition="BLOB") 
	private byte[] photo;
	 
	public String getId() {
		return id;
	}
	 
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public byte[] getPhoto() {
		return photo;
	}
	
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
}
