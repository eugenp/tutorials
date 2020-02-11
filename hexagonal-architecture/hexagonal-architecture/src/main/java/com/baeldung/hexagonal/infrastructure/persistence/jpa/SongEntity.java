package com.baeldung.hexagonal.infrastructure.persistence.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Profile;

import lombok.Data;

@Profile("jpa")
@Entity
@Table(name = "SONG")
@Data
public class SongEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer songId;

    @Column
    private String name;

    @Column
    private String description;
}
