package com.baeldung.difftypesdependencyinjection;

import org.springframework.stereotype.Component;

/**
 * 
 * @author haseeb
 *
 */
@Component
public class Student {

	private int id;
	private String firstName;
	private String lastName;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * full name of student
	 * @return
	 */
	public String name() {
		return firstName +" "+ lastName; 
	}
}
