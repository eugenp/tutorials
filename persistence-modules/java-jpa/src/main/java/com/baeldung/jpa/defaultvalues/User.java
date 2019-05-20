package com.baeldung.jpa.defaultvalues;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    private Long id;

    @Column(columnDefinition = "varchar(255) default 'John Snow'")
    private String name = "John Snow";

    @Column(columnDefinition = "integer default 25")
    private Integer age = 25;

    @Column(columnDefinition = "boolean default false")
    private Boolean locked = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
}
