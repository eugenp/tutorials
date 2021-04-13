package com.baeldung.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class User implements Serializable{
	private static final long serialVersionUID = -0x234F2882D07B96BBL;
	private String id;
	private String name;
	private List<String> skills = new ArrayList<>();
}
