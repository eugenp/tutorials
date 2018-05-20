package com.baeldung.webfluxdemo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Event {
	private String title;
	private Date eventTime;
	// standard setters and getters
}
