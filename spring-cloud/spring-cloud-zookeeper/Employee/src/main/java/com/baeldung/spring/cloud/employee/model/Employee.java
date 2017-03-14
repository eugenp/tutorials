/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.spring.cloud.employee.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {

	@NotNull
	private String lastName;
	private String firstName;
	private String emailAddress;
	private Boolean validRecord;

	public Employee() {

	}

	public String getlastName() {
		return this.lastName;
	}

	public String getfirstName() {
		return this.firstName;
	}

	public String getemailAddress() {
		return this.emailAddress;
	}

	public Boolean getvalidRecord() {
		return this.validRecord;
	}

	public void setlastName(String value) {
		this.lastName = value;
	}

	public void setfirstName(String value) {
		this.firstName = value;
	}

	public void setemailAddress(String value) {
		this.emailAddress = value;
	}

	public void setvalidRecord(Boolean value) {
		this.validRecord = value;
	}

}
