package com.baeldung.webflux.model;

import java.io.Serializable;

public class Event implements Serializable {

    private static final long serialVersionUID = -6415713305955411683L;
    private Long id;
    private String type;

    public Event(Long id, String type) {
	super();
	this.id = id;
	this.type = type;
    }

    public Long getId() {
	return id;
    }

    public String getType() {
	return type;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public void setType(String type) {
	this.type = type;
    }

    @Override
    public String toString() {
	return String.format("Event [id=%s, type=%s]", id, type);
    }

}
