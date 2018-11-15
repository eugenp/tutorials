package com.baeldung.reactivedebugging.consumer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FooDto {

	private Integer id;
	private String name;
}
