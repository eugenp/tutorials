package com.baeldung.envers.customrevision.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Data
@NoArgsConstructor
public class Species {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    public static Species forName(String name) {
        var s = new Species();
        s.setName(name);
        return s;
    }
}
