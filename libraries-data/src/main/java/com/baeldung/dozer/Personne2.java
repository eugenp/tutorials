package com.baeldung.dozer;

import org.dozer.Mapping;

public class Personne2 {
    private String nom;
    private String surnom;
    private int age;

    public Personne2() {

    }

    public Personne2(String nom, String surnom, int age) {
        super();
        this.nom = nom;
        this.surnom = surnom;
        this.age = age;
    }

    @Mapping("name")
    public String getNom() {
        return nom;
    }

    @Mapping("nickname")
    public String getSurnom() {
        return surnom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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

}
