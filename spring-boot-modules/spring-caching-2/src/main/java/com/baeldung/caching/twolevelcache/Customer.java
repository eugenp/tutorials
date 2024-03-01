package com.baeldung.caching.twolevelcache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Customer implements Serializable {

    private String id;

    private String name;

    private String email;
}