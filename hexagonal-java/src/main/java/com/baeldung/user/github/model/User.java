package com.baeldung.user.github.model;

import lombok.ToString;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

/**
 * Model for the Entity User
 */
@Entity
@ToString
@Table(name = "users")
public class User extends AbstractEntity{
	/**
	 * User's firstname
	 */
	@Column(name = "first_name", nullable = false)
	@NotNull
    private String firstName;
	
	/**
	 * User's surName
	 */
	@Column(name = "sur_name")
    private String surName;
	
	/**
	 * User's position
	 */
	@Column(name = "position")
    private String position;
	
	/**
	 * User's github profile url
	 */
	@Column(name = "git_prof_rel_url")
	@NotNull
	@URL(protocol = "https")
    private String gitProfRelUrl;
	
	/**
	 * Getters and Setters
	 */
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getGitProfRelUrl() {
		return gitProfRelUrl;
	}

	public void setGitProfRelUrl(String gitProfRelUrl) {
		this.gitProfRelUrl = gitProfRelUrl;
	}

}
