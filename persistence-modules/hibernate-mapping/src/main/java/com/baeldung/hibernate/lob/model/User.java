package com.baeldung.hibernate.lob.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity 
@Table(name="user")
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
