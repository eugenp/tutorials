package com.baeldung.hexagonal.erros;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ConflictError {

	private int code;
	private String message;
	
}
