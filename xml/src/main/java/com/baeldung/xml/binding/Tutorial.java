package com.baeldung.xml.binding;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Tutorial {

	private String tutId;
	private String type;
	private String title;
	private String description;
	private Date date;
	private String author;
	
	
	public String getTutId() {
		return tutId;
	}
	
	@XmlAttribute
	public void setTutId(String tutId) {
		this.tutId = tutId;
	}
	public String getType() {
		return type;
	}
	@XmlAttribute
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	@XmlElement
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	@XmlElement
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return date;
	}
	@XmlElement
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAuthor() {
		return author;
	}
	@XmlElement
	public void setAuthor(String author) {
		this.author = author;
	}
}
