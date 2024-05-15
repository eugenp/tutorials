package com.baeldung.hamcrest;

public class Animal {
	String name;
	boolean wild;
	String sound;
	
	public Animal(String name, boolean wild, String sound) {
		super();
		this.name = name;
		this.wild = wild;
		this.sound = sound;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isWild() {
		return wild;
	}
	public void setWild(boolean wild) {
		this.wild = wild;
	}
	public String getSound() {
		return sound;
	}
	public void setSound(String sound) {
		this.sound = sound;
	}
	
}
