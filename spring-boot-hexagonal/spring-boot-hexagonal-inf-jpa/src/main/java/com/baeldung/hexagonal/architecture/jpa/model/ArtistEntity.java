package com.baeldung.hexagonal.architecture.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ARTIST")
@Data
public class ArtistEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column
    private Long artistId;

    @Column
    private String name;

}
