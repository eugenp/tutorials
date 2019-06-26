package com.baeldung.hexagonalArchitecture.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

@Entity
@Table(name = "message", schema ="msg" )
@JsonIgnoreType
public class Message {

	@Id
	@GeneratedValue
	@Column
	private Long id;
	
	@Column
	private String message;
	
	@Column
	private String message_type;

	public Message() {
		super();
	}

	public Message(Long id, String message, String message_type) {
		super();
		this.id = id;
		this.message = message;
		this.message_type = message_type;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the message_type
	 */
	public String getMessage_type() {
		return message_type;
	}

	/**
	 * @param message_type the message_type to set
	 */
	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}
	
	
	
}
