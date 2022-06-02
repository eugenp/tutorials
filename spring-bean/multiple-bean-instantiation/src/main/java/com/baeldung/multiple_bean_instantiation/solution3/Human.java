package com.baeldung.multiple_bean_instantiation.solution3;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/*@Component*/
public class Human implements InitializingBean{
	
	private Person personOne;

	private Person personTwo;

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(personOne, "Harold is alive!");
		Assert.notNull(personTwo, "John is alive!");
	}

	/* Setter injection */
	public void setPersonOne(Person personOne) {
		this.personOne = personOne;
		this.personOne.setFirstName("Harold");
		this.personOne.setSecondName("Finch");
	}

	public void setPersonTwo(Person personTwo) {
		this.personTwo = personTwo;
		this.personTwo.setFirstName("John");
		this.personTwo.setSecondName("Reese");
	} 
}
