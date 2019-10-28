package com.baeldung.spring.data.reactive.redis.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Employee implements Serializable {
    private static final long serialVersionUID = 1603714798906422731L;
    private String id;
    private String name;
    private String department;
}
