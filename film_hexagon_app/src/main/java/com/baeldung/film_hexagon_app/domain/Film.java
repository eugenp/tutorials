package com.baeldung.film_hexagon_app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Core of the application - Business domain
 */
@Setter
@Getter
@Entity
public class Film {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	private String type;
	private String description;
	private int saison;
	private String duration;
}


