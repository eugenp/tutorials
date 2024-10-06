package com.baeldung.jsonb;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbNumberFormat;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;

public class Person {

    private int id;
    @JsonbProperty("person-name")
    private String name;
    @JsonbProperty(nillable = true)
    private String email;
    @JsonbTransient
    private int age;
    @JsonbDateFormat("dd-MM-yyyy")
    private LocalDate registeredDate;
    private BigDecimal salary;

    public Person() {
        this(0, "", "", 0, LocalDate.now(), new BigDecimal(0));
    }

    public Person(int id, String name, String email, int age, LocalDate registeredDate, BigDecimal salary) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.registeredDate = registeredDate;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonbNumberFormat(locale = "en_US", value = "#0.0")
    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(LocalDate registeredDate) {
        this.registeredDate = registeredDate;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Person [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", email=");
        builder.append(email);
        builder.append(", age=");
        builder.append(age);
        builder.append(", registeredDate=");
        builder.append(registeredDate);
        builder.append(", salary=");
        builder.append(salary);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        if (id != other.id)
            return false;
        return true;
    }

}
