package com.baeldung.spring.reactive.springreactiveexceptions.model;

import lombok.Data;

import java.util.List;

@Data
public class User {
	private String _id;
	private String index;
	private String guid;
	private Boolean isActive;
	private String balance;
	private String picture;
	private Integer age;
	private String eyeColor;
	private String name;
	private String gender;
	private String company;
	private String email;
	private String phone;
	private String address;
	private String about;
	private String registered;
	private Integer latitude;
	private Integer longitude;
	private List<String> tags;
	private List<Friend> friends;
	private String greeting;
	private String favouriteFruit;
}
