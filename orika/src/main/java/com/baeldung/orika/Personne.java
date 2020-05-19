package com.baeldung.orika;

public class Personne {
	private String nom;
	private String surnom;
	private int age;

	public Personne(String nom, String surnom, int age) {
		this.nom = nom;
		this.surnom = surnom;
		this.age = age;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getSurnom() {
		return surnom;
	}

	public void setSurnom(String surnom) {
		this.surnom = surnom;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Personne [nom=" + nom + ", surnom=" + surnom + ", age=" + age
				+ "]";
	}

}
