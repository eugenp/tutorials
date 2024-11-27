package com.baeldung.envers.customrevision.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.envers.Audited;

import java.util.List;

@Entity
@Audited
@Data
public class Owner {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Pet> pets;

    public static Owner forName(String name ) {

        var owner = new Owner();
        owner.setName(name);
        return owner;
    }

}
