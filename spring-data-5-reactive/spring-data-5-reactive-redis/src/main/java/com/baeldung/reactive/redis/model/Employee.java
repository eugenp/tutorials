package com.baeldung.reactive.redis.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RedisHash
@EqualsAndHashCode
public class Employee implements Serializable {
    @Id
    private String id;
    private String name;
    private String department;
}
