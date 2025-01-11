package com.baeldung.springaistructuredoutput.dto;

public class Character {

    private String name;
    private int age;
    private String race;
    private String characterClass;
    private String cityOfOrigin;
    private String favoriteWeapon;
    private String bio;

    public Character(String name, int age, String characterClass, String shortBio) {
        this.name = name;
        this.age = age;
        this.characterClass = characterClass;
    }

    public Character() {}

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public String getBio() {
        return bio;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void setCharacterClass(String characterClass) {
        this.characterClass = characterClass;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCityOfOrigin() {
        return cityOfOrigin;
    }

    public void setCityOfOrigin(String cityOfOrigin) {
        this.cityOfOrigin = cityOfOrigin;
    }

    public String getFavoriteWeapon() {
        return favoriteWeapon;
    }

    public void setFavoriteWeapon(String favoriteWeapon) {
        this.favoriteWeapon = favoriteWeapon;
    }
}
