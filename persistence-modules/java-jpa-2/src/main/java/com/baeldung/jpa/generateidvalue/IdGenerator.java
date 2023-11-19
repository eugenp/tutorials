package com.baeldung.jpa.generateidvalue;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "id_gen")
@Entity
public class IdGenerator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gen_name")
    private String gen_name;

    @Column(name = "gen_value")
    private Long gen_value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGen_name() {
        return gen_name;
    }

    public void setGen_name(String gen_name) {
        this.gen_name = gen_name;
    }

    public Long getGen_value() {
        return gen_value;
    }

    public void setGen_value(Long gen_value) {
        this.gen_value = gen_value;
    }

}