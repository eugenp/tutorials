package com.ehidiamen.simplewebfluxhello2.model;

public class Show {
	
	  private String id;
	  private String title;

	  public Show(String id, String title) {
	    this.id = id;
	    this.title = title;
	  }

	  public String getId() {
	    return id;
	  }

	  public void setId(String id) {
	    this.id = id;
	  }

	  public String getTitle() {
	    return title;
	  }

	  public void setTitle(String title) {
	    this.title = title;
	  }
}
