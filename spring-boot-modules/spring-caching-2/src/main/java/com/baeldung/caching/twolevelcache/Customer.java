package com.baeldung.caching.twolevelcache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
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