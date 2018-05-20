package com.baeldung.webclientdemo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Event {
	private String title;
	private Date eventTime;
}
