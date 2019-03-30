package com.baeldung.domain;

import java.time.LocalDate;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data @AllArgsConstructor
public class User {
	private String name;
	private String surname;
	private String email;
	private LocalDate birthDate;
	
	public boolean isBirthday(int monthValue, int dayOfMonth) {
		return this.birthDate.getDayOfMonth() == dayOfMonth 
				&& this.birthDate.getMonthValue() == monthValue;
	}
}
