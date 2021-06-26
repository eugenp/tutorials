package com.baeldung.hexagon.architecture.domain.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Book {

	@Id
	private String title;
	private String refNumber;
	private String author;
	private Date yearOfPublication;
	private boolean issueStatus;
	private Date issueDate;

}
