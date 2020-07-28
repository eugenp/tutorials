package com.baeldung.hexagonal.application.request;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReturnBookRequest {

	@NotNull private String title;
	
	@JsonCreator
	public ReturnBookRequest(@JsonProperty("title") final String title)
	{
		this.title = title;
	}
	
	public String getBookTitle() {
		return title;
	}
}