package com.baeldung.port.outbound.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserEntity{
	@GeneratedValue
	@Id
	private UUID id;
	private String name;
	@ElementCollection
	private List<String> skills = new ArrayList<>();
}