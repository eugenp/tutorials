package com.baeldung.envers.customrevision.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Audited
@Data
public class Pet {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private UUID uuid;

    private String name;

    // A null ownes implies the pet is available for adoption
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = true)
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "species_id")
    private Species species;
}
