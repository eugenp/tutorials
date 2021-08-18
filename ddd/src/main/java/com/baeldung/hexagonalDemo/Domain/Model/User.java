package com.hexagon.hexagon_architecture.Domain.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {

    @JsonProperty("id")
    private String userId;

    @JsonProperty("name")
    private String name;

    //getter setter and default constructor
}
