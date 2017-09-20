package com.baeldung.jsonb;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbNumberFormat;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;

public class Person {

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
    }

    public Person(String name, String email, int age, LocalDate registeredDate, BigDecimal salary) {
        super();
        this.name = name;
        this.email = email;
        this.age = age;
        this.registeredDate = registeredDate;
        this.salary = salary;
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
        builder.append("Person [name=");
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
        result = prime * result + age;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((registeredDate == null) ? 0 : registeredDate.hashCode());
        result = prime * result + ((salary == null) ? 0 : salary.hashCode());
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
        if (age != other.age)
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (registeredDate == null) {
            if (other.registeredDate != null)
                return false;
        } else if (!registeredDate.equals(other.registeredDate))
            return false;
        if (salary == null) {
            if (other.salary != null)
                return false;
        } else if (!salary.equals(other.salary))
            return false;
        return true;
    }

}
