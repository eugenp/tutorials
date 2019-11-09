package com.baeldung.tapestry.pages;

import org.apache.tapestry5.annotations.PageActivationContext;

public class About {

	@PageActivationContext
	private String learn;

	public String getLearn() {
		return learn;
	}

	public void setLearn(String learn) {
		this.learn = learn;
	}

}
