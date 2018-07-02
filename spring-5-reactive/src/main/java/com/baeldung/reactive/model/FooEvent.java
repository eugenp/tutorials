package com.baeldung.reactive.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FooEvent {
	private long id;
	private String name;
}
