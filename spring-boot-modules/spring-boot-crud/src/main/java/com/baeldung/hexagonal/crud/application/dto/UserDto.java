package com.baeldung.hexagonal.crud.application.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {

    private static final long serialVersionUID = 637212422369187321L;
    private String name;
    private String email;

    public UserDto(String name, String email){
        this.name = name;
        this.email = email;
    }
}
