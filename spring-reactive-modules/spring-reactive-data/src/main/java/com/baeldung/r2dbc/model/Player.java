package com.baeldung.r2dbc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table
public class Player {
    @Id
    Integer id;
    String name;
    Integer age;

    public Player() {
    }

    public Player(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}