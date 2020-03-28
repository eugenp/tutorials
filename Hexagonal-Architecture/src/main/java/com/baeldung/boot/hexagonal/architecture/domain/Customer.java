package com.baeldung.boot.hexagonal.architecture.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Customer {
	private Long customerId;
	private String name;
	private String emailAddress;
	private Double contactNo;
	private String city;
	private String state;
	private String country;
}
