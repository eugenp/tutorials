package com.baeldung.suppliercallable.data;

import java.time.LocalDate;

public class User {

    private String name;
    private String surname;
    private LocalDate birthDate;
    private Integer age;
    private Boolean canDriveACar = false;

    public User(String name, String surname, LocalDate birthDate) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

    public Boolean getCanDriveACar() {
        return canDriveACar;
    }

    public void setCanDriveACar(Boolean canDriveACar) {
        this.canDriveACar = canDriveACar;
    }

}
