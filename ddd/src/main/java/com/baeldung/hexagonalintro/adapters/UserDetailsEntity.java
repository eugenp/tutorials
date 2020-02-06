package com.baeldung.hexagonalintro.adapters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
class UserDetailsEntity {

    @Id
    private String username;

    private String password;
}
