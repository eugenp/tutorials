package com.baeldung.caching.twolevelcache;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Customer implements Serializable {

    @Id
    private String id;

    private String name;

    private String email;
}