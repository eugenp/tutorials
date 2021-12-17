package com.baeldung.spring.taglibrary;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

public class Person {

    private long id;

    private String name;
    private String email;
    private String dateOfBirth;

    @NotEmpty
    private String password;
    private String sex;
    private String country;
    private String book;
    private String job;
    private boolean receiveNewsletter;
    private String[] hobbies;
    private List<String> favouriteLanguage;
    private List<String> fruit;
    private String notes;
    private MultipartFile file;

    public Person() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(final String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(final String sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getJob() {
        return job;
    }

    public void setJob(final String job) {
        this.job = job;
    }

    public boolean isReceiveNewsletter() {
        return receiveNewsletter;
    }

    public void setReceiveNewsletter(final boolean receiveNewsletter) {
        this.receiveNewsletter = receiveNewsletter;
    }

    public String[] getHobbies() {
        return hobbies;
    }

    public void setHobbies(final String[] hobbies) {
        this.hobbies = hobbies;
    }

    public List<String> getFavouriteLanguage() {
        return favouriteLanguage;
    }

    public void setFavouriteLanguage(final List<String> favouriteLanguage) {
        this.favouriteLanguage = favouriteLanguage;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(final String notes) {
        this.notes = notes;
    }

    public List<String> getFruit() {
        return fruit;
    }

    public void setFruit(final List<String> fruit) {
        this.fruit = fruit;
    }

    public String getBook() {
        return book;
    }

    public void setBook(final String book) {
        this.book = book;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(final MultipartFile file) {
        this.file = file;
    }
}
