package com.baeldung.reactivedebugging.consumer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FooDao {
	
	@Id
	private Integer id;
	private String formattedName;
	private Integer quantity;

	public FooDao(FooDto dto) {
		this.id = dto.getId();
		this.formattedName = dto.getName();
	}
}
