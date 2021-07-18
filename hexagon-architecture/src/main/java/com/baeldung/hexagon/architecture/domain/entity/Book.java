package com.baeldung.hexagon.architecture.domain.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Book {

	@Id
	private Long id;
	private String title;
	private String refNumber;
	private String author;
	private Date yearOfPublication;
	private boolean issueStatus;
	private Date issueDate;

}
